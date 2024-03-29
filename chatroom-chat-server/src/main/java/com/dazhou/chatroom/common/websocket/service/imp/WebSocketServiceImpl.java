package com.dazhou.chatroom.common.websocket.service.imp;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.dazhou.chatroom.common.common.event.UserOnlineEvent;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.IpInfo;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.enums.RoleEnum;
import com.dazhou.chatroom.common.user.service.IRoleService;
import com.dazhou.chatroom.common.user.service.IUserRoleService;
import com.dazhou.chatroom.common.user.service.LoginService;
import com.dazhou.chatroom.common.websocket.NettyUtil;
import com.dazhou.chatroom.common.websocket.domain.dto.WSChannelExtraDTO;
import com.dazhou.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
import com.dazhou.chatroom.common.websocket.domain.vo.req.WSBaseReq;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WSLoginUrl;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WsBaseResp;
import com.dazhou.chatroom.common.websocket.service.WebSocketService;
import com.dazhou.chatroom.common.websocket.service.adapter.WebSocketAdapter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-03 20:22
 * 专门管理websocket的逻辑，包括推拉
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDao userDao;

    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private IRoleService roleService;
    @Autowired
    @Qualifier(value = "websocketExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * 管理所有用户的连接（登录态/游客）
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP=new ConcurrentHashMap<>();

    public static final Duration DURATION = Duration.ofHours(1);

    public static final int MAXIMUM_SIZE = 1000;
    /**
     * 临时保存登录code和channel的映射关系
     */
    private static final Cache<Integer,Channel> WAIT_LOGIN_MAP= Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)   //设置最大容量
            .expireAfterWrite(DURATION)  //设置过期时间
            .build();

    /**
     * 将channel与用户绑定
     * @param channel
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel,new WSChannelExtraDTO());
    }

    /**
     * 将channel与用户解绑
     * @param channel
     */
    @Override
    public void offline(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
        //todo 用户下线
    }



    /**
     * 生成code，申请二维码返回给前端
     * @param channel
     */
    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        //生成随机码
        Integer code=generateLoginCode(channel);
        //找微信申请带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        //封装信息，把码推送给前端
        sendMsg(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    private void sendMsg(Channel channel, WsBaseResp<?> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    private Integer generateLoginCode(Channel channel) {
        Integer code;
        do{
            //随机生成code
            code= RandomUtil.randomInt(Integer.MAX_VALUE);
            //把生成的code设置到WAIT_LOGIN_MAP中，将这个code和channel绑定
        }while (Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code,channel)));   //code必须不一样才能设置成功 成功返回Null
        return code;
    }
    @Override
    public void scanLoginSuccess(Integer code, Long id) {
        //确定连接在机器上
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)){
            return;
        }
        User user = userDao.getById(id);
        //移除code
        WAIT_LOGIN_MAP.invalidate(code);
        //获取登录模块获取Token
        String token=loginService.login(id);
        //封装信息 返回给前端
        sendMsg(channel,WebSocketAdapter.buildResp(user,token));
    }

    @Override
    public void waitAuthorize(Integer code) {
        //确定连接在机器上
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)){
            return;
        }
        sendMsg(channel,WebSocketAdapter.buildWaitAuthorizeResp());

    }

    @Override
    public void authorizes(Channel channel, String token) {
        Long validUid = loginService.getValidUid(token);
        //token在有效期内
        if (Objects.nonNull(validUid)){
            User user = userDao.getById(validUid);
            loginSuccess(channel,user,token);
        }else{
            //提醒用户重新登录
            sendMsg(channel,WebSocketAdapter.buildWaitAuthorizeResp());
        }
    }

    @Override
    public void sendMsgToAll(WsBaseResp<?> msg) {
        ONLINE_WS_MAP.forEach((channel,ext)->{
            threadPoolTaskExecutor.execute(()->{
                sendMsg(channel,msg);
            });

        });
    }

    private void loginSuccess(Channel channel, User user, String token) {
        //保存channel的对应uid
        WSChannelExtraDTO wsChannelExtraDTO=ONLINE_WS_MAP.get(channel);
        wsChannelExtraDTO.setUid(user.getId());
        //推送成功信息
        sendMsg(channel,WebSocketAdapter.buildResp(user,token,roleService.hasPower(user.getId(), RoleEnum.CHAT_MANAGER)));
        //用户上线成功的事件
        user.setLastOptTime(new Date());
        //更新用户ip
        user.refreshIp(NettyUtil.getAttr(channel,NettyUtil.IP));
        //用户上线事件发布
        applicationEventPublisher.publishEvent(new UserOnlineEvent(this,user));
    }
}

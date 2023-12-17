package com.dazhou.chatroom.common.websocket.service.imp;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
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
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-03 20:22
 * 专门管理websocket的逻辑，包括推拉
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private WxMpService wxMpService;

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
        //把码推送给前端
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
}

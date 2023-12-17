package com.dazhou.chatroom.common.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.service.UserService;
import com.dazhou.chatroom.common.user.service.WXMsgService;
import com.dazhou.chatroom.common.user.service.adapter.TextBuilder;
import com.dazhou.chatroom.common.user.service.adapter.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-17 22:26
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {
    public static final String URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    @Value("${wx.mp.callback}")
    private String callback;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);
        if (Objects.isNull(code)){
            return null;
        }
        //根据openId获取用户
        User user = userDao.getByOpenId(openId);
        boolean registered = Objects.nonNull(user);
        boolean authorized = registered && StrUtil.isNotBlank(user.getAvatar());
        //用户已经注册并授权
        if ( authorized ){
            //todo 走登录成功逻辑，通过code找到channel推送消息
        }
        //用户未注册,就先注册
        if (!registered){
            //构建user对象，将opendId设置到user对象中
            User insert = UserAdapter.buildUserSave(openId);
            //先保存用户，里面数据只有userId和openId 后面在设置进去
            userService.register(insert);
        }
        //推送链接让用户授权
        String authorizeUrl=String.format(URL,wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback+"/wx/portal/public/callBack"));
        return TextBuilder.build("请点击链接授权：<a href=\"" + authorizeUrl + "\">登录</a>",wxMpXmlMessage);
        //
    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {

    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        //todo 事件码 qrscene_2
        //如果是新关注这个公众号的code 会多qrscene_2这个字符串
        try {
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            log.error("getEventKey error eventKey:{}",wxMpXmlMessage.getEventKey(),e);
            throw null;
        }
    }
}

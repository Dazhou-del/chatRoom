package com.dazhou.chatroom.common.websocket.service.adapter;

import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WSLoginSuccess;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WSLoginUrl;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WsBaseResp;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-03 20:56
 */
public class WebSocketAdapter {
    /**
     * 把码推送给前端
     * @param wxMpQrCodeTicket
     * @return
     */
    public static WsBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WsBaseResp<WSLoginUrl> resp = new WsBaseResp<>();
        //设置类型
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        //设置url
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return  resp;
    }

    /**
     * 用户成功登录后 返回的信息
     * @param user
     * @param token
     * @return
     */
    public static WsBaseResp<?> buildResp(User user, String token) {
        WsBaseResp<WSLoginSuccess> resp=new WsBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess build = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .uid(user.getId())
                .build();
        resp.setData(build);
        return  resp;
    }

    /**
     * 扫码成功等待授权
     * @return
     */
    public static WsBaseResp<?> buildWaitAuthorizeResp() {
        WsBaseResp<WSLoginUrl> resp = new WsBaseResp<>();
        //设置类型
        resp.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());

        return  resp;
    }
}

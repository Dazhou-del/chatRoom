package com.dazhou.chatroom.common.websocket.service.adapter;

import com.dazhou.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
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
}

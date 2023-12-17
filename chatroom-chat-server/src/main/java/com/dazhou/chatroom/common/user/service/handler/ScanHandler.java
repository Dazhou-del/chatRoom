package com.dazhou.chatroom.common.user.service.handler;


import com.dazhou.chatroom.common.user.service.WXMsgService;
import com.dazhou.chatroom.common.user.service.adapter.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

@Component
public class ScanHandler extends AbstractHandler {



    @Autowired
    private WXMsgService wxMsgService;

    /**
     * 不是新用户扫码进入这个方法
     * @param wxMpXmlMessage
     * @param map
     * @param wxMpService
     * @param wxSessionManager
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        return wxMsgService.scan(wxMpXmlMessage);

    }

}

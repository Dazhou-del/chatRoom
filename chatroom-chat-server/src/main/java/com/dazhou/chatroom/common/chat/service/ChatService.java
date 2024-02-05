package com.dazhou.chatroom.common.chat.service;

import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessageReq;
import com.dazhou.chatroom.common.chat.domain.vo.response.ChatMessageResp;

/**
 * 消息处理类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:24
 */
public interface ChatService {
    /**
     * 发送消息
     * @param request
     * @param uid
     * @return
     */
    Long sendMsg(ChatMessageReq request, Long uid);

    /**
     * 根据消息获取消息前端展示的物料
     * @param msgId
     * @param uid
     * @return
     */
    ChatMessageResp getMsgResp(Long msgId, Long uid);
}

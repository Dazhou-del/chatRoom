package com.dazhou.chatroom.common.websocket.service;

import io.netty.channel.Channel;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-03 20:22
 */
public interface WebSocketService {

    void connect(Channel channel);

    /**
     * 二维码地址
     * @param channel
     */
    void handleLoginReq(Channel channel);
}

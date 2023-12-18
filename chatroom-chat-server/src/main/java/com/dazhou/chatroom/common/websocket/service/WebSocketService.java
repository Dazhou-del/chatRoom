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

    /**
     * channel 断开连接
     * @param channel
     */
    void offline(Channel channel);

    /**
     * 扫码成功后
     * @param code
     * @param id
     */
    void scanLoginSuccess(Integer code, Long id);

    /**
     * 发送等待授权的消息给前端
     * @param code
     */
    void waitAuthorize(Integer code);
}

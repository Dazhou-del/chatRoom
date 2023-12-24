package com.dazhou.chatroom.common.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;

import com.dazhou.chatroom.common.websocket.domain.enums.WSReqTypeEnum;
import com.dazhou.chatroom.common.websocket.domain.vo.req.WSBaseReq;
import com.dazhou.chatroom.common.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        webSocketService.connect(ctx.channel());
    }

    /**
     * 用户主动下线，用户断开连接会触发这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffline(ctx.channel());
    }

    //成功连接后会进入这个方法
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StrUtil.isNotBlank(token)){
                webSocketService.authorizes(ctx.channel(),token);
            }
            System.out.println("握手完成");
        } else if (evt instanceof IdleStateEvent) {
            //通过这个的状态判断
            IdleStateEvent event = (IdleStateEvent) evt;
            //如果超过设置的读空闲时间 可以在这里做业务处理
            //
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("读空闲");
                //用户下线
                userOffline(ctx.channel());
            }
        }

    }

    /**
     * 用户下线统一处理
     *
     * @param channel
     */
    private void userOffline(Channel channel) {
        webSocketService.offline(channel);
        //让连接关闭
        channel.close();
    }


    /**
     * 客户端发送消息 在这里接收
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN:
                webSocketService.handleLoginReq(ctx.channel());
            case HEARTBEAT:
            case AUTHORIZE:
                webSocketService.authorizes(ctx.channel(),wsBaseReq.getData());

        }
    }
}

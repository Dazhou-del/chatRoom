package com.dazhou.chatroom.common.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;

import com.dazhou.chatroom.common.user.domain.enums.WSReqTypeEnum;
import com.dazhou.chatroom.common.user.domain.enums.WSRespTypeEnum;
import com.dazhou.chatroom.common.user.domain.vo.req.WSBaseReq;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.json.Id;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            System.out.println("握手完成");
        }else if (evt instanceof IdleStateEvent){
            //通过这个的状态判断
            IdleStateEvent event = (IdleStateEvent) evt;
            //如果超过设置的读空闲时间 可以在这里做业务处理
            //
            if (event.state()==IdleState.READER_IDLE){
                System.out.println("读空闲");
                //todo 用户下线

            }
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())){
            case LOGIN:
                System.out.println("请求二维码");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("123"));
            case  HEARTBEAT:
            case  AUTHORIZE:
        }
    }
}

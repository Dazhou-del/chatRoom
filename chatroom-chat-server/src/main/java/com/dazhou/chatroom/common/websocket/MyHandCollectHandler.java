package com.dazhou.chatroom.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Optional;

/**握手认证
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-24 21:42
 */
public class MyHandCollectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            //获取token
            String token = Optional.ofNullable(urlBuilder.getQuery()).map(k->k.get("token")).map(CharSequence::toString).orElse("");
            NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token);
            //移除后面拼接的所有参数
            request.setUri(urlBuilder.getPath().toString());

        }
        ctx.fireChannelRead(msg);
    }
}

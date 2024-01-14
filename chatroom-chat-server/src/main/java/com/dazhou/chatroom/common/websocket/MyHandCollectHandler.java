package com.dazhou.chatroom.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
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
            //保存到channel附件
            NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token);
            //移除后面拼接的所有参数
            request.setUri(urlBuilder.getPath().toString());
            //取用户id
            String ip = request.headers().get("X-Real-IP");
            if(StringUtils.isBlank(ip)){
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip=address.getAddress().getHostAddress();
            }
            //保存到channel附件
            NettyUtil.setAttr(ctx.channel(), NettyUtil.IP, ip);
            //移除掉,处理器只需要用一次
            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);
    }
}

package com.dazhou.chatroom.common.websocket;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.jar.Attributes;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-24 21:47
 */
public class NettyUtil {
    public static AttributeKey<String> TOKEN=AttributeKey.valueOf("token");
    public static AttributeKey<String> IP=AttributeKey.valueOf("ip");

    public static <T> void setAttr(Channel channel, AttributeKey<T> key,T value){
        Attribute<T> attr = channel.attr(key);
        attr.set(value);
    }
    public static <T> T getAttr(Channel channel, AttributeKey<T> key){
        Attribute<T> attr = channel.attr(key);
        return attr.get();
    }
}

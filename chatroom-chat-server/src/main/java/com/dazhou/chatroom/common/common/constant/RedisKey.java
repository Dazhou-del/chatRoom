package com.dazhou.chatroom.common.common.constant;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-19 21:59
 */
public class RedisKey {
    private static final String BASE_KEY="chatroom:chat";
    /**
     * 用户token的可以
     */
    public static final String USER_TOKEN_STRING="userToken:uid_%d";

    public static String getKey(String key,Object... o){
        return BASE_KEY+String.format(key,o);
    }
}

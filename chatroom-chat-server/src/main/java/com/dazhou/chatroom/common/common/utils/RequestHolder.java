package com.dazhou.chatroom.common.common.utils;

import com.dazhou.chatroom.common.common.domain.dto.RequestInfo;

/**
 * 请求上下文threadLocal
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 22:26
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}

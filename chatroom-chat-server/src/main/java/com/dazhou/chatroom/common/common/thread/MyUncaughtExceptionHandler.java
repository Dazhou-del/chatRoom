package com.dazhou.chatroom.common.common.thread;

import lombok.extern.slf4j.Slf4j;

/**重写 UncaughtExceptionHandle方法
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-20 22:16
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread"+e);
    }
}

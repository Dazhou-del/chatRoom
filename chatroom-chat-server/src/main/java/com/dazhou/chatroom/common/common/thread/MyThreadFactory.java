package com.dazhou.chatroom.common.common.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/** 装饰器
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-20 22:15
 */
@AllArgsConstructor
public class MyThreadFactory implements ThreadFactory {
    private static final MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER=new MyUncaughtExceptionHandler();

    private ThreadFactory original;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = original.newThread(r);//执行spring线程自己的创建逻辑
        //额外装饰我们需要的创建逻辑
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);//异常捕获
        return thread;
    }
}

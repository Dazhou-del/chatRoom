package com.dazhou.chatroom.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式注解
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 0:32
 */
@Retention(RetentionPolicy.RUNTIME) //运行时生效
@Target(ElementType.METHOD)//作用在方法上
public @interface RedisssonLock {

    /**
     * key的前缀，其默认取方法全限定名，除非我们在不同方法上对同一个资源做分布式锁，就自己指定
     * 方法名+权限名
     * @return
     */
    String prefixKey() default "";

    /**
     * redisson的可以 要求支持springEl 表达式
     * @return
     */
    String key();

    /**
     * 等待锁的时间，默认-1，不等待直接失败,redisson默认也是-1
     * @return
     */
    int waitTime() default -1;

    /**
     * 等待锁的时间单位，默认毫秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}

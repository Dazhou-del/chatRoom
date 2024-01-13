package com.dazhou.chatroom.common.common.service;

import com.dazhou.chatroom.common.common.exception.BusinessException;
import com.dazhou.chatroom.common.common.exception.CommonErrorEnum;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 分布式锁工具类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-13 23:57
 */
@Service
public class LockService {
    @Autowired
    private RedissonClient redissonClient;

    //@SneakyThrows 注解可以使方法在遇到异常时，自动将异常转换为 java.lang.RuntimeException 并抛出，而无需显式地在方法中编写异常处理代码。

    /**
     * 统一实现分布式锁
     * @param key 分布式锁key
     * @param waitTime  等待时间
     * @param timeUnit  时间单位
     * @param supplier 锁内的代码块用supplier函数传进来
     * @return
     * @param <T>
     */
    @SneakyThrows
    public <T> T executeWithLock(String key, int waitTime, TimeUnit timeUnit, Supplier<T> supplier){
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(waitTime, timeUnit);
        if (!success){
            throw new BusinessException(CommonErrorEnum.LOCK_LIMIT);
        }
        try {
            return supplier.get();//执行锁内的代码逻辑
        }finally {
            lock.unlock();
        }
    }

    public <T> T executeWithLock(String key,Supplier<T> supplier) {
       return   executeWithLock(key,-1,TimeUnit.MICROSECONDS,supplier);
    }

    public <T> T executeWithLock(String key,Runnable runnable) {
        return  executeWithLock(key,-1,TimeUnit.MICROSECONDS,()->{
            runnable.run();
            return null;
        });
    }
    public interface Supplier<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }
}

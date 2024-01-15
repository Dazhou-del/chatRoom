package com.dazhou.chatroom.common.common.config;

import com.dazhou.chatroom.common.common.thread.MyThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/** 线程池配置
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-20 21:32
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * 项目共用线程池
     */
    public static final String MALLCHAT_EXECUTOR = "chatRoomExecutor";

    /**
     * websocket通信线程池
     */
    public static final String WS_EXECUTOR = "websocketExecutor";

    @Override
    public Executor getAsyncExecutor() {
        return chatRoomExecutor();
    }

    //通过名字找到是那个线程池
    @Bean(MALLCHAT_EXECUTOR)
    @Primary
    public ThreadPoolTaskExecutor chatRoomExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //这个设置是 在程序关闭时 是直接关闭线程 还是等待任务的执行完毕才关闭。false是直接关闭 所以这里改成true
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        //设置前缀 出了问题方便排除
        executor.setThreadNamePrefix("chatRoom-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//满了调用线程执行，认为重要任务
        //设置自定义异常处理
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.initialize();
        return executor;
    }

    @Bean(WS_EXECUTOR)
    @Primary
    public ThreadPoolTaskExecutor websocketExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //这个设置是 在程序关闭时 是直接关闭线程 还是等待任务的执行完毕才关闭。false是直接关闭 所以这里改成true
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(10000);
        //设置前缀 出了问题方便排除
        executor.setThreadNamePrefix("websocket-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());//满了直接丢弃
        //设置自定义异常处理
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.initialize();
        return executor;
    }
}


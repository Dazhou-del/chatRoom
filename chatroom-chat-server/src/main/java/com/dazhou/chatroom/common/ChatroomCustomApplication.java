package com.dazhou.chatroom.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zhongzb
 * @date 2021/05/27
 */
@SpringBootApplication(scanBasePackages = {"com.dazhou.chatroom"})

public class ChatroomCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatroomCustomApplication.class,args);
    }

}
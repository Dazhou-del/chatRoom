package com.dazhou.chatroom.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2023-12-03
 */
@SpringBootApplication(scanBasePackages = {"com.dazhou.chatroom"})
@MapperScan({"com.dazhou.chatroom.common.**.mapper"})
public class ChatroomCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatroomCustomApplication.class,args);
    }

}
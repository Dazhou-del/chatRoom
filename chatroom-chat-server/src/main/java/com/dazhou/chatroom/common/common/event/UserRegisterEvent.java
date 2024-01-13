package com.dazhou.chatroom.common.common.event;

import com.dazhou.chatroom.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 1:28
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private User user;
    public UserRegisterEvent(Object source,User user) {
        super(source);
        this.user=user;
    }
}

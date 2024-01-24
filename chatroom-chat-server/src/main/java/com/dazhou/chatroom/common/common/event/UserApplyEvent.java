package com.dazhou.chatroom.common.common.event;

import com.dazhou.chatroom.common.user.domain.entity.UserApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:45
 */
@Getter
public class UserApplyEvent extends ApplicationEvent {
    private UserApply userApply;

    public UserApplyEvent(Object source, UserApply userApply) {
        super(source);
        this.userApply = userApply;
    }

}

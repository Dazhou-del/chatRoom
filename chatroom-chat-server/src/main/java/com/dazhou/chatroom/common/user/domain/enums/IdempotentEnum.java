package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-13 23:28
 */
@AllArgsConstructor
@Getter
public enum IdempotentEnum {
    UID(1,"uid"),
    MSG_ID(2,"消息id");


    private final Integer tpe;
    private final String desc;
}

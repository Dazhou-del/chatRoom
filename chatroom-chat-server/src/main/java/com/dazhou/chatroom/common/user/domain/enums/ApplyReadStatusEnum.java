package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请阅读状态枚举
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:50
 */
@Getter
@AllArgsConstructor
public enum ApplyReadStatusEnum {

    UNREAD(1, "未读"),

    READ(2, "已读");

    private final Integer code;

    private final String desc;
}

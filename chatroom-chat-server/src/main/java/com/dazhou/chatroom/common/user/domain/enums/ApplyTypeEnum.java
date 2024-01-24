package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请类型枚举
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:22
 */
@Getter
@AllArgsConstructor
public enum ApplyTypeEnum {

    ADD_FRIEND(1, "加好友");


    private final Integer code;

    private final String desc;
}


package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态枚举
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:21
 */
@Getter
@AllArgsConstructor
public enum ApplyStatusEnum {

    WAIT_APPROVAL(1, "待审批"),

    AGREE(2, "同意");

    private final Integer code;

    private final String desc;
}

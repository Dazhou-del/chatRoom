package com.dazhou.chatroom.common.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:26
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {
    No(0,"否"),
    YES(1,"是");

    private final Integer status;
    private final String desc;
}

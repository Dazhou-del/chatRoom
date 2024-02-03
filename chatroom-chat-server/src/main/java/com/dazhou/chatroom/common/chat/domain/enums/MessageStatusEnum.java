package com.dazhou.chatroom.common.chat.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息状态
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:05
 */
@AllArgsConstructor
@Getter
public enum MessageStatusEnum {
    NORMAL(0, "正常"),
    DELETE(1, "删除"),
    ;

    private final Integer status;
    private final String desc;

    private static Map<Integer, MessageStatusEnum> cache;

    static {
        cache = Arrays.stream(MessageStatusEnum.values()).collect(Collectors.toMap(MessageStatusEnum::getStatus, Function.identity()));
    }

    public static MessageStatusEnum of(Integer type) {
        return cache.get(type);
    }
}

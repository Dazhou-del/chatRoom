package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息状态
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:36
 */
@AllArgsConstructor
@Getter
public enum RoomTypeEnum {
    GROUP(1, "群聊"),
    FRIEND(2, "单聊"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, RoomTypeEnum> cache;

    static {
        cache = Arrays.stream(RoomTypeEnum.values()).collect(Collectors.toMap(RoomTypeEnum::getType, Function.identity()));
    }

    public static RoomTypeEnum of(Integer type) {
        return cache.get(type);
    }
}

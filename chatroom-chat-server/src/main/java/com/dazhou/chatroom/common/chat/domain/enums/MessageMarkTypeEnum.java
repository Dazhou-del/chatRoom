package com.dazhou.chatroom.common.chat.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息标记类型
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:07
 */
@AllArgsConstructor
@Getter
public enum MessageMarkTypeEnum {
    LIKE(1, "点赞", 10),
    DISLIKE(2, "点踩", 5),
    ;

    private final Integer type;
    private final String desc;
    private final Integer riseNum;//需要多少个标记升级

    private static Map<Integer, MessageMarkTypeEnum> cache;

    static {
        cache = Arrays.stream(MessageMarkTypeEnum.values()).collect(Collectors.toMap(MessageMarkTypeEnum::getType, Function.identity()));
    }

    public static MessageMarkTypeEnum of(Integer type) {
        return cache.get(type);
    }
}

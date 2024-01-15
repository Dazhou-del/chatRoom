package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-15 21:39
 */
@AllArgsConstructor
@Getter
public enum BlackTypeEnum {
    UIP(2,"UID"),
    IP(1,"IP")
    ;

    private final Integer id;
    private final String desc;

    private static Map<Integer, BlackTypeEnum> cache;

    static {
        cache = Arrays.stream(BlackTypeEnum.values()).collect(Collectors.toMap(BlackTypeEnum::getId, Function.identity()));
    }

    public static BlackTypeEnum of(Integer id) {
        return cache.get(id);
    }

}

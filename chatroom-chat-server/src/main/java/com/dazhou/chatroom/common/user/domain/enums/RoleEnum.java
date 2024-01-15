package com.dazhou.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 权限枚举
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:21
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN(1l,"超级管理员"),
    CHAT_MANAGER(2l,"群聊权限")
    ;

    private final Long id;
    private final String desc;

    private static Map<Long, RoleEnum> cache;

    static {
        cache = Arrays.stream(RoleEnum.values()).collect(Collectors.toMap(RoleEnum::getId, Function.identity()));
    }

    public static RoleEnum of(Long id) {
        return cache.get(id);
    }
}


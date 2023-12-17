package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.entity.User;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2023-12-03
 */
public interface UserService {

    /**
     * 用户注册
     * @param insert
     */
    Long register(User insert);
}

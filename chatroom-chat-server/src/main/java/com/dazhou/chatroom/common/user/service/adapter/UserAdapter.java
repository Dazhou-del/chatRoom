package com.dazhou.chatroom.common.user.service.adapter;

import com.dazhou.chatroom.common.user.domain.entity.User;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-17 22:45
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }
}

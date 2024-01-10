package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;

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

    /**
     * 获取用户详细信息
     * @param uid
     * @return
     */
    UserInfoResp getUserInfo(Long uid);

    /**
     * 修改用户名
     * @param uid
     * @param name
     */
    void modifyName(Long uid, String name);
}

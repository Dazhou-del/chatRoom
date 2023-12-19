package com.dazhou.chatroom.common.user.service;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-18 20:53
 */
public interface LoginService {

    /**
     * 刷新token有效期
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取token
     * @param uid
     * @return token
     */
    String login(Long uid);

    /**
     * 如果token有效，返回uid
     * @param token
     * @return
     */
    Long getValidUid(String token);

}

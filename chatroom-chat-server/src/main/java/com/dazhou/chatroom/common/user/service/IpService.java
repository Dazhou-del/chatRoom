package com.dazhou.chatroom.common.user.service;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 20:54
 */
public interface IpService {
    /**
     * 异步更新用户ip详情解析
     * @param uid
     */
    void refreshIpDetailAsync(Long uid);
}

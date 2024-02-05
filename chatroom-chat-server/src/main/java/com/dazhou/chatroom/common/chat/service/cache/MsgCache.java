package com.dazhou.chatroom.common.chat.service.cache;

import com.dazhou.chatroom.common.chat.dao.MessageDao;
import com.dazhou.chatroom.common.chat.domain.entity.Message;
import com.dazhou.chatroom.common.user.dao.BlackDao;
import com.dazhou.chatroom.common.user.dao.RoleDao;
import com.dazhou.chatroom.common.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 消息相关缓存
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-05 19:28
 */
@Component
public class MsgCache {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BlackDao blackDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MessageDao messageDao;

    @Cacheable(cacheNames = "msg", key = "'msg'+#msgId")
    public Message getMsg(Long msgId) {
        return messageDao.getById(msgId);
    }

    @CacheEvict(cacheNames = "msg", key = "'msg'+#msgId")
    public Message evictMsg(Long msgId) {
        return null;
    }
}

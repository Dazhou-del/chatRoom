package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.Contact;

import com.dazhou.chatroom.common.chat.mapper.ContactMapper;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:32
 */
@Service
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {
    public Contact get(Long uid, Long roomId) {
        return lambdaQuery()
                .eq(Contact::getUid, uid)
                .eq(Contact::getRoomId, roomId)
                .one();
    }
}

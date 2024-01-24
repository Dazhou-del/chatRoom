package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.Contact;
import com.dazhou.chatroom.common.user.mapper.ContactMapper;
import com.dazhou.chatroom.common.user.service.IContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会话列表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-24
 */
@Service
public class ContactDao extends ServiceImpl<ContactMapper, Contact> implements IContactService {

}

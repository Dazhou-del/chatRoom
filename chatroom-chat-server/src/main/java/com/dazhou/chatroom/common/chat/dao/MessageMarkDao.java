package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.MessageMark;
import com.dazhou.chatroom.common.chat.mapper.MessageMarkMapper;
import org.springframework.stereotype.Service;

/**
 * 消息标记表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:35
 */
@Service
public class MessageMarkDao extends ServiceImpl<MessageMarkMapper, MessageMark> {
}

package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.GroupMember;
import com.dazhou.chatroom.common.chat.mapper.GroupMemberMapper;
import org.springframework.stereotype.Service;

/**
 * 群成员表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:34
 */
@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {
}

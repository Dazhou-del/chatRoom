package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.RoomFriend;
import com.dazhou.chatroom.common.chat.mapper.RoomFriendMapper;
import org.springframework.stereotype.Service;

/**
 * 单聊房间表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:36
 */
@Service
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {
    public RoomFriend getByRoomId(Long roomID) {
        return lambdaQuery()
                .eq(RoomFriend::getRoomId, roomID)
                .one();
    }
}

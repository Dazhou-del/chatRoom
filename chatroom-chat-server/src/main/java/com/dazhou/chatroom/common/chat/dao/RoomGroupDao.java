package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.RoomGroup;
import com.dazhou.chatroom.common.chat.mapper.RoomGroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 群聊房间表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:36
 */
@Service
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {
    public List<RoomGroup> listByRoomIds(List<Long> roomIds) {
        return lambdaQuery()
                .in(RoomGroup::getRoomId, roomIds)
                .list();
    }
}

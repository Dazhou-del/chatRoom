package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.entity.RoomFriend;

import java.util.List;

/**
 * 房间底层管理
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:31
 */
public interface RoomService {
    /**
     * 创建一个单聊房间
     */
    RoomFriend createFriendRoom(List<Long> uidList);
}

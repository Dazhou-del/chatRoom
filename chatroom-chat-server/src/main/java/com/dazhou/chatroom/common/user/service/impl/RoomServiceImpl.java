package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.RoomDao;
import com.dazhou.chatroom.common.user.dao.RoomFriendDao;
import com.dazhou.chatroom.common.user.domain.entity.Room;
import com.dazhou.chatroom.common.user.domain.entity.RoomFriend;
import com.dazhou.chatroom.common.user.domain.enums.NormalOrNoEnum;
import com.dazhou.chatroom.common.user.domain.enums.RoomTypeEnum;
import com.dazhou.chatroom.common.user.service.RoomService;
import com.dazhou.chatroom.common.user.service.adapter.ChatAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:32
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomFriendDao roomFriendDao;

    @Resource
    private RoomDao roomDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomFriend createFriendRoom(List<Long> uidList) {
        AssertUtil.isNotEmpty(uidList, "房间创建失败，好友数量不对");
        AssertUtil.equal(uidList.size(), 2, "房间创建失败，好友数量不对");
        String key = ChatAdapter.generateRoomKey(uidList);

        RoomFriend roomFriend = roomFriendDao.getByKey(key);
        if (Objects.nonNull(roomFriend)) { //如果存在房间就恢复，适用于恢复好友场景
            restoreRoomIfNeed(roomFriend);
        } else {//新建房间
            Room room = createRoom(RoomTypeEnum.FRIEND);
            roomFriend = createFriendRoom(room.getId(), uidList);
        }
        return roomFriend;
    }

    private RoomFriend createFriendRoom(Long roomId, List<Long> uidList) {
        RoomFriend insert = ChatAdapter.buildFriendRoom(roomId, uidList);
        roomFriendDao.save(insert);
        return insert;
    }
    private void restoreRoomIfNeed(RoomFriend room) {
        if (Objects.equals(room.getStatus(), NormalOrNoEnum.NOT_NORMAL.getStatus())) {
            roomFriendDao.restoreRoom(room.getId());
        }
    }
    private Room createRoom(RoomTypeEnum typeEnum) {
        Room insert = ChatAdapter.buildRoom(typeEnum);
        roomDao.save(insert);
        return insert;
    }
}

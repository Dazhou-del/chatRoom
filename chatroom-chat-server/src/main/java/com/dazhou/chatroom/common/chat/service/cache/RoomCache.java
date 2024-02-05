package com.dazhou.chatroom.common.chat.service.cache;

import com.dazhou.chatroom.common.chat.dao.RoomDao;
import com.dazhou.chatroom.common.chat.dao.RoomFriendDao;
import com.dazhou.chatroom.common.chat.domain.entity.Room;
import com.dazhou.chatroom.common.common.constant.RedisKey;
import com.dazhou.chatroom.common.common.service.cache.AbstractRedisStringCache;

import com.dazhou.chatroom.common.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 房间基本信息的缓存
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 2:04
 */
@Component
public class RoomCache extends AbstractRedisStringCache<Long, Room> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomFriendDao roomFriendDao;

    @Override
    protected String getKey(Long roomId) {
        return RedisKey.getKey(RedisKey.ROOM_INFO_STRING, roomId);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    @Override
    protected Map<Long, Room> load(List<Long> roomIds) {
        List<Room> rooms = roomDao.listByIds(roomIds);
        return rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
    }
}
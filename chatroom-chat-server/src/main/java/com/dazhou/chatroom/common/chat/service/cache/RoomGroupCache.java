package com.dazhou.chatroom.common.chat.service.cache;

import com.dazhou.chatroom.common.chat.dao.RoomGroupDao;
import com.dazhou.chatroom.common.chat.domain.entity.RoomGroup;
import com.dazhou.chatroom.common.common.constant.RedisKey;
import com.dazhou.chatroom.common.common.service.cache.AbstractRedisStringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 群组基本信息的缓存
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 2:09
 */
@Component
public class RoomGroupCache extends AbstractRedisStringCache<Long, RoomGroup> {
    @Autowired
    private RoomGroupDao roomGroupDao;

    @Override
    protected String getKey(Long roomId) {
        return RedisKey.getKey(RedisKey.GROUP_INFO_STRING, roomId);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    @Override
    protected Map<Long, RoomGroup> load(List<Long> roomIds) {
        List<RoomGroup> roomGroups = roomGroupDao.listByRoomIds(roomIds);
        return roomGroups.stream().collect(Collectors.toMap(RoomGroup::getRoomId, Function.identity()));
    }
}


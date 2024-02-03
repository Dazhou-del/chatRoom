package com.dazhou.chatroom.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazhou.chatroom.common.chat.domain.entity.Room;
import com.dazhou.chatroom.common.chat.mapper.RoomMapper;
import org.springframework.stereotype.Service;

/**
 * 房间表 服务实现类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:35
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room> implements IService<Room> {
}

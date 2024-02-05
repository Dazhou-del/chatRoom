package com.dazhou.chatroom.common.chat.service.impl;

import com.dazhou.chatroom.common.chat.dao.GroupMemberDao;
import com.dazhou.chatroom.common.chat.dao.RoomFriendDao;
import com.dazhou.chatroom.common.chat.domain.entity.GroupMember;
import com.dazhou.chatroom.common.chat.domain.entity.Room;
import com.dazhou.chatroom.common.chat.domain.entity.RoomFriend;
import com.dazhou.chatroom.common.chat.domain.entity.RoomGroup;
import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessageReq;
import com.dazhou.chatroom.common.chat.domain.vo.response.ChatMessageResp;
import com.dazhou.chatroom.common.chat.service.ChatService;
import com.dazhou.chatroom.common.chat.service.cache.RoomCache;
import com.dazhou.chatroom.common.chat.service.cache.RoomGroupCache;
import com.dazhou.chatroom.common.chat.service.strategy.AbstractMsgHandler;
import com.dazhou.chatroom.common.chat.service.strategy.MsgHandlerFactory;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.domain.enums.NormalOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息处理类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 0:29
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private RoomGroupCache roomGroupCache;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Override
    public Long sendMsg(ChatMessageReq request, Long uid) {
        check(request, uid);
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNoNull(request.getMsgType());
        Long msgId = msgHandler.checkAndSaveMsg(request, uid);
        //发布消息发送事件
//        applicationEventPublisher.publishEvent(new MessageSendEvent(this, msgId));
        return msgId;
    }

    private void check(ChatMessageReq request, Long uid) {
        Room room = roomCache.get(request.getRoomId());
        if (room.isHotRoom()) {//全员群跳过校验
            return;
        }
        if (room.isRoomFriend()) {
            RoomFriend roomFriend = roomFriendDao.getByRoomId(request.getRoomId());
            AssertUtil.equal(NormalOrNoEnum.NORMAL.getStatus(), roomFriend.getStatus(), "您已经被对方拉黑");
            AssertUtil.isTrue(uid.equals(roomFriend.getUid1()) || uid.equals(roomFriend.getUid2()), "您已经被对方拉黑");
        }
        if (room.isRoomGroup()) {
            RoomGroup roomGroup = roomGroupCache.get(request.getRoomId());
            GroupMember member = groupMemberDao.getMember(roomGroup.getId(), uid);
            AssertUtil.isNotEmpty(member, "您已经被移除该群");
        }

    }
    @Override
    public ChatMessageResp getMsgResp(Long msgId, Long uid) {
        return null;
    }
}

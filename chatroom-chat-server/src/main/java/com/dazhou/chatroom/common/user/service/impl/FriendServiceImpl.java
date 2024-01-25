package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.annotation.RedisssonLock;
import com.dazhou.chatroom.common.common.event.UserApplyEvent;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.UserApplyDao;
import com.dazhou.chatroom.common.user.dao.UserFriendDao;
import com.dazhou.chatroom.common.user.domain.entity.RoomFriend;
import com.dazhou.chatroom.common.user.domain.entity.UserApply;
import com.dazhou.chatroom.common.user.domain.entity.UserFriend;
import com.dazhou.chatroom.common.user.domain.enums.ApplyStatusEnum;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendApplyReq;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendApproveReq;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendCheckReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.FriendCheckResp;
import com.dazhou.chatroom.common.user.service.FriendService;
import com.dazhou.chatroom.common.user.service.RoomService;
import com.dazhou.chatroom.common.user.service.adapter.FriendAdapter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-23 22:23
 * 好友
 */
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private UserFriendDao userFriendDao;
    @Autowired
    private UserApplyDao userApplyDao;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public FriendCheckResp check(Long uid, FriendCheckReq request) {
        //在user_friend表中查出 当前用户id与好友id是否有数据 如果有数据则证明是好友
        List<UserFriend> friendList = userFriendDao.getByFriends(uid, request.getUidList());
        //拿出是好友的用户id
        Set<Long> friendUidSet = friendList.stream().map(UserFriend::getFriendUid).collect(Collectors.toSet());
        //将当前用户id和是好友的id组成FriendCheck返回
        List<FriendCheckResp.FriendCheck> friendCheckList = request.getUidList().stream().map(friendUid -> {
            FriendCheckResp.FriendCheck friendCheck = new FriendCheckResp.FriendCheck();
            friendCheck.setUid(friendUid);
            friendCheck.setIsFriend(friendUidSet.contains(friendUid));
            return friendCheck;
        }).collect(Collectors.toList());
        return new FriendCheckResp(friendCheckList);
    }


    @Override
    public void apply(Long uid, FriendApplyReq request) {
        //1.判断是否有好友关系
        UserFriend friend = userFriendDao.getByFriend(uid, request.getTargetUid());
        AssertUtil.isEmpty(friend, "你们已经是好友了");
        //2.判断是否有待审批的申请记录（自己的) 查待审批申请类型为加好友的  是否有我向别人发送好友申请的记录
        UserApply selfApproving = userApplyDao.getFriendApproving(uid, request.getTargetUid());
        if (Objects.nonNull(selfApproving)) {
            log.info("已有好友申请记录,uid:{}, targetId:{}", uid, request.getTargetUid());
            return;
        }
        //3.判断是否有待审批的申请记录(别人请求自己的) 是否有别人向我发送好友请求
        UserApply friendApproving = userApplyDao.getFriendApproving(request.getTargetUid(), uid);
        if (Objects.nonNull(friendApproving)) {
            //如果有记录就直接同意好友申请
            //同意好友申请
            ((FriendService) AopContext.currentProxy()).applyApprove(uid, new FriendApproveReq(friendApproving.getId()));
            return;
        }
        //4.申请入库
        UserApply insert = FriendAdapter.buildFriendApply(uid, request);
        userApplyDao.save(insert);
        //5.申请事件
//        applicationEventPublisher.publishEvent(new UserApplyEvent(this, insert));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisssonLock(key = "#uid")
    public void applyApprove(Long uid, FriendApproveReq request) {
        UserApply userApply = userApplyDao.getById(request.getApplyId());
        AssertUtil.isNotEmpty(userApply, "不存在申请记录");
        AssertUtil.equal(userApply.getTargetId(), uid, "不存在申请记录");
        AssertUtil.equal(userApply.getStatus(), ApplyStatusEnum.WAIT_APPROVAL.getCode(), "已同意好友申请");
        //同意申请
        userApplyDao.agree(request.getApplyId());
        //创建双方好友关系
        createFriend(uid, userApply.getUid());
        //创建一个聊天房间
        RoomFriend roomFriend = roomService.createFriendRoom(Arrays.asList(uid, userApply.getUid()));
        //发送一条同意消息。。我们已经是好友了，开始聊天吧
//        chatService.sendMsg(MessageAdapter.buildAgreeMsg(roomFriend.getRoomId()), uid);
    }

    private void createFriend(Long uid, Long targetUid) {
        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUid(uid);
        userFriend1.setFriendUid(targetUid);
        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUid(targetUid);
        userFriend2.setFriendUid(uid);
        userFriendDao.saveBatch(Lists.newArrayList(userFriend1, userFriend2));
    }
}

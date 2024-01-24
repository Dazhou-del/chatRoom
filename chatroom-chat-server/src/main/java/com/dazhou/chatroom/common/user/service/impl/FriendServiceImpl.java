package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.user.dao.UserFriendDao;
import com.dazhou.chatroom.common.user.domain.entity.UserFriend;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendCheckReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.FriendCheckResp;
import com.dazhou.chatroom.common.user.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

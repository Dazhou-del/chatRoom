package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.UserFriend;
import com.dazhou.chatroom.common.user.mapper.UserFriendMapper;
import com.dazhou.chatroom.common.user.service.IUserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户联系人表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-23
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> implements IUserFriendService {

    public List<UserFriend> getByFriends(Long uid, List<Long> uidList) {
        return lambdaQuery().eq(UserFriend::getUid,uid)
                .in(UserFriend::getFriendUid, uidList)
                .list();
    }

    public UserFriend getByFriend(Long uid, Long targetUid) {
        return lambdaQuery().eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, targetUid)
                .one();
    }
}

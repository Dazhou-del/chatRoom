package com.dazhou.chatroom.common.common.event.lister;

import com.dazhou.chatroom.common.common.event.UserApplyEvent;
import com.dazhou.chatroom.common.user.dao.UserApplyDao;
import com.dazhou.chatroom.common.user.domain.entity.UserApply;
import com.dazhou.chatroom.common.user.service.PushService;
import com.dazhou.chatroom.common.websocket.domain.vo.resp.WSFriendApply;
import com.dazhou.chatroom.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 好友申请监听器
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:46
 */
@Slf4j
@Component
public class UserApplyListener {
    @Autowired
    private UserApplyDao userApplyDao;
    @Autowired
    private WebSocketService webSocketService;

//    @Autowired
//    private PushService pushService;

    @Async
    @TransactionalEventListener(classes = UserApplyEvent.class, fallbackExecution = true)
    public void notifyFriend(UserApplyEvent event) {
        UserApply userApply = event.getUserApply();
        Integer unReadCount = userApplyDao.getUnReadCount(userApply.getTargetId());
//        pushService.sendPushMsg(WSAdapter.buildApplySend(new WSFriendApply(userApply.getUid(), unReadCount)), userApply.getTargetId());
    }

}

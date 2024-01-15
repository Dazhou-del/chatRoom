package com.dazhou.chatroom.common.common.event.lister;

import com.dazhou.chatroom.common.common.event.UserBlackEvent;
import com.dazhou.chatroom.common.common.event.UserOnlineEvent;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.enums.ChatActiveStatusEnum;
import com.dazhou.chatroom.common.user.service.IpService;
import com.dazhou.chatroom.common.user.service.cache.UserCache;
import com.dazhou.chatroom.common.websocket.service.WebSocketService;
import com.dazhou.chatroom.common.websocket.service.adapter.WebSocketAdapter;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户拉黑事件
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 1:28
 */
@Component
public class UserBlackListener  {
    @Autowired
    private IpService ipService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private UserCache userCache;

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class,phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void sendCard(UserBlackEvent event){
        User user = event.getUser();
        //通知其他用户
        webSocketService.sendMsgToAll(WebSocketAdapter.buildBlack(user));
    }

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class,phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void changeUserStatus(UserBlackEvent event){
       userDao.invalidUid(event.getUser().getId());
    }

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class,phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void evictCache(UserBlackEvent event){
        //清空缓存
        userCache.evictBlackMap();
    }
}

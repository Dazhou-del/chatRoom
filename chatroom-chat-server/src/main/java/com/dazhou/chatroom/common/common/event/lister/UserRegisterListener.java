package com.dazhou.chatroom.common.common.event.lister;

import com.dazhou.chatroom.common.common.event.UserRegisterEvent;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.enums.IdempotentEnum;
import com.dazhou.chatroom.common.user.domain.enums.ItemEnum;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户注册事件监听者
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 1:31
 */
@Component
public class UserRegisterListener {

    @Autowired
    private IUserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class,phase = TransactionPhase.AFTER_COMMIT)
    public void sendCard(UserRegisterEvent event){
        User user = event.getUser();
        //发放改名卡
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID,user.getId().toString());
    }

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class,phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event){
        User user = event.getUser();
        int count = userDao.count();
        if (count<10){
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID,user.getId().toString());
        } else if (count<100) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID,user.getId().toString());
        }
        //发放改名卡
        
    }
}

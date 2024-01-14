package com.dazhou.chatroom.common.common.event.lister;

import com.dazhou.chatroom.common.common.event.UserOnlineEvent;
import com.dazhou.chatroom.common.common.event.UserRegisterEvent;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.enums.ChatActiveStatusEnum;
import com.dazhou.chatroom.common.user.domain.enums.IdempotentEnum;
import com.dazhou.chatroom.common.user.domain.enums.ItemEnum;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import com.dazhou.chatroom.common.user.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserOnlineListener {

    @Autowired
    private IpService ipService;
    @Autowired
    private UserDao userDao;

    @Async
    @TransactionalEventListener(classes = UserOnlineEvent.class,phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void sendCard(UserOnlineEvent event){
        User user = event.getUser();
        //更新用户信息
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(ChatActiveStatusEnum.ONLINE.getStatus());
        userDao.updateById(update);
        //用户ip详情解析
        ipService.refreshIpDetailAsync(user.getId());
    }


}

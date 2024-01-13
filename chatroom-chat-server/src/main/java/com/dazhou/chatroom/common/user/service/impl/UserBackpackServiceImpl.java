package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.annotation.RedisssonLock;
import com.dazhou.chatroom.common.common.domain.enums.YesOrNoEnum;
import com.dazhou.chatroom.common.common.service.LockService;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.UserBackpackDao;
import com.dazhou.chatroom.common.user.domain.entity.UserBackpack;
import com.dazhou.chatroom.common.user.domain.enums.IdempotentEnum;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-13 23:33
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
    @Autowired
    private LockService lockService;

    @Autowired
    private UserBackpackDao userBackpackDao;

    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;
    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        //获取幂等号
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        userBackpackService.doAcquireItem(uid,itemId,idempotent);

    }
    //同类调用这样子注解会失效 有两种解决方法
    //方法一： 自己注入自己，然后在通过注入的bean去调用方法，但是这样会导致循环依赖 需要加Lazy注解 这里使用的就是这种方法
    //方法二： 使用 ((UserBackpackServiceImpl)AopContext.currentProxy()).doAcquireItem(uid,itemId,idempotent);

    @RedisssonLock(key = "#idempotent",waitTime = 5000)
    public void doAcquireItem(Long uid,Long itemId,String idempotent){
        //判断该幂等是否存在
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) return;
        //发放物品
        UserBackpack insert=UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.No.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }

    /**
     * 获取幂等号
     * @param itemId
     * @param idempotentEnum
     * @param businessId
     * @return
     */
    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s",itemId,idempotentEnum.getTpe(),businessId);
    }
}

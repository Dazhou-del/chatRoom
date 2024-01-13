package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.domain.enums.YesOrNoEnum;
import com.dazhou.chatroom.common.common.utils.AssertUtil;
import com.dazhou.chatroom.common.user.dao.UserBackpackDao;
import com.dazhou.chatroom.common.user.domain.entity.UserBackpack;
import com.dazhou.chatroom.common.user.domain.enums.IdempotentEnum;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-13 23:33
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserBackpackDao userBackpackDao;
    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        //获取幂等号
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        //获取锁
        RLock lock = redissonClient.getLock("acquireItem" + idempotent);
        boolean b = lock.tryLock();
        //判断锁是否存在  如果不是true 也就是没有获取到锁 则直接抛出异常
        AssertUtil.isTrue(b,"请求太频繁");
        try {
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
        }finally {
            lock.unlock();
        }
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

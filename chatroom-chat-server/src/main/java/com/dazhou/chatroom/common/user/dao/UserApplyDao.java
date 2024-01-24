package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.UserApply;
import com.dazhou.chatroom.common.user.domain.enums.ApplyStatusEnum;
import com.dazhou.chatroom.common.user.domain.enums.ApplyTypeEnum;
import com.dazhou.chatroom.common.user.mapper.UserApplyMapper;
import com.dazhou.chatroom.common.user.service.IUserApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-23
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> implements IUserApplyService {

    public UserApply getFriendApproving(Long uid, Long targetUid) {
        return lambdaQuery().eq(UserApply::getUid, uid)
                .eq(UserApply::getTargetId, targetUid)
                .eq(UserApply::getStatus, ApplyStatusEnum.WAIT_APPROVAL)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND.getCode())
                .one();
    }

    public void agree(Long applyId) {
        lambdaUpdate().set(UserApply::getStatus, ApplyStatusEnum.AGREE.getCode())
                .eq(UserApply::getId, applyId)
                .update();
    }
}

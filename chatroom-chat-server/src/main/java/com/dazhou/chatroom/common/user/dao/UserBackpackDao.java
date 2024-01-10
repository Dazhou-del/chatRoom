package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.common.domain.enums.YesOrNoEnum;
import com.dazhou.chatroom.common.user.domain.entity.UserBackpack;
import com.dazhou.chatroom.common.user.mapper.UserBackpackMapper;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-07
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack>  {

    public Integer getCountByValidItemId(Long uid, Long itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.No.getStatus())
                .count();
    }
}

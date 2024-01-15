package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.user.domain.entity.Black;
import com.dazhou.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.dazhou.chatroom.common.user.domain.enums.RoleEnum;
import com.dazhou.chatroom.common.user.domain.vo.req.BlackReq;
import com.dazhou.chatroom.common.user.service.IRoleService;
import com.dazhou.chatroom.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-15 20:37
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        //全部权限
        Set<Long> roleSet = userCache.getRoleSet(uid);
        //判断用户是否有这个权限
        return isAdmin(roleSet)|| roleSet.contains(roleEnum.getId());
    }



    private boolean isAdmin(Set<Long> roleSet){
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }
}

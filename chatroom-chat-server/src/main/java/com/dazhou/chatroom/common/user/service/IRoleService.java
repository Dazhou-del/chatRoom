package com.dazhou.chatroom.common.user.service;

import com.dazhou.chatroom.common.user.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dazhou.chatroom.common.user.domain.enums.RoleEnum;
import com.dazhou.chatroom.common.user.domain.vo.req.BlackReq;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-14
 */
public interface IRoleService  {

    /**
     * 是否拥有某个权限，临时写法
     * @param uid
     * @param roleEnum
     * @return
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);


}

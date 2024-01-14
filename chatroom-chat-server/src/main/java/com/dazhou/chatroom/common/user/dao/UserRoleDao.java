package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.UserRole;
import com.dazhou.chatroom.common.user.mapper.UserRoleMapper;
import com.dazhou.chatroom.common.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-14
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}

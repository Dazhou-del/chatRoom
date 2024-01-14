package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.Role;
import com.dazhou.chatroom.common.user.mapper.RoleMapper;
import com.dazhou.chatroom.common.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2024-01-14
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

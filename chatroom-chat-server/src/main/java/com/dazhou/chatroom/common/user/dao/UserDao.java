package com.dazhou.chatroom.common.user.dao;

import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.mapper.UserMapper;
import com.dazhou.chatroom.common.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2023-12-03
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User>  {


    public User getByOpenId(String openId){
        return lambdaQuery()
                .eq(User::getOpenId, openId)
                .one();
    }


    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }
}

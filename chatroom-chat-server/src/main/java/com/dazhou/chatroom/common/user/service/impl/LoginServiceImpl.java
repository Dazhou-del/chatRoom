package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.constant.RedisKey;
import com.dazhou.chatroom.common.common.utils.JwtUtils;
import com.dazhou.chatroom.common.common.utils.RedisUtils;
import com.dazhou.chatroom.common.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-18 20:53
 */
@Service
public class LoginServiceImpl implements LoginService {
    //登录时间
    public static final int TOKEN_EXPIRE_DAYS = 3;
    public static final int  TOKEN_RENEWAL_DAYS= 1;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        //没有时间了返回-2
        Long expireDays = RedisUtils.getExpire(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid), TimeUnit.DAYS);
        if (expireDays == -2) {//不存在的key 不做处理
            return;
        }
        if (expireDays < TOKEN_RENEWAL_DAYS) {
            RedisUtils.expire(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid), TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        Boolean set = RedisUtils.set(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        //获取旧的token 不为空说明都在有效期内
        String oldToken = RedisUtils.get(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid), String.class);
        return Objects.equals(oldToken, token) ? uid : null;
    }

}

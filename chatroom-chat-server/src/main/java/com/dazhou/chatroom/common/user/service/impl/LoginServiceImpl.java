package com.dazhou.chatroom.common.user.service.impl;

import com.dazhou.chatroom.common.common.utils.JwtUtils;
import com.dazhou.chatroom.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-18 20:53
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        return null;
    }

    @Override
    public Long getValidUid(String token) {
        return null;
    }
}

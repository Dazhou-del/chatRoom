package com.dazhou.chatroom.common;

import cn.hutool.json.JSONUtil;
import com.dazhou.chatroom.common.common.utils.RedisUtils;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.service.LoginService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dazhou
 * @title
 * @create 2023-12-03 16:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private LoginService loginService;

    @Test
    public void test(){
        User byId = userDao.getById(1);
        System.out.println(byId);
    }

    @Test
    public void redis(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDA0LCJjcmVhdGVUaW1lIjoxNzAyOTk1NjcyfQ.9-m4IJSGZGabrHrlI6FVqUXaj57jImBVmnjxfbBY3pQ";
        Long uid = loginService.getValidUid(token);
        System.out.println(uid);
    }

    @Test
    public void getWxToken() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 10000);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }
}

package com.dazhou.chatroom.common;

import cn.hutool.json.JSONUtil;
import com.dazhou.chatroom.common.common.config.ThreadPoolConfig;
import com.dazhou.chatroom.common.common.utils.RedisUtils;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.domain.enums.IdempotentEnum;
import com.dazhou.chatroom.common.user.domain.enums.ItemEnum;
import com.dazhou.chatroom.common.user.service.IUserBackpackService;
import com.dazhou.chatroom.common.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dazhou
 * @title
 * @create 2023-12-03 16:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {
    public static final long UID = 11004L;
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
    public void jwt(){
        //Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDA0LCJjcmVhdGVUaW1lIjoxNzA0ODA5NTE3fQ.Wi_k6fzr4O-vFlXgt_pd6p-5ijOLEPOtrFtkSzR6eEs
        String login = loginService.login(UID);
        System.out.println(login);
    }

    @Autowired
    private IUserBackpackService iUserBackpackService;
    @Test
    public void acquireItem(){
        iUserBackpackService.acquireItem(UID, ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID,UID+"");
    }
    @Test
    public void test(){
        User byId = userDao.getById(1);
        System.out.println(byId);
    }
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Test
    public void ThreadTest() throws InterruptedException {
        threadPoolTaskExecutor.execute(()->{
            if (1==1){
                log.error("123");
                throw new RuntimeException("1234");
            }
        });
        Thread.sleep(200);
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

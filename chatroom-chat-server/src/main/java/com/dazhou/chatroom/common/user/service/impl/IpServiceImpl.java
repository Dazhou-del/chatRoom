package com.dazhou.chatroom.common.user.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.user.dao.UserDao;
import com.dazhou.chatroom.common.user.domain.entity.IpDetail;
import com.dazhou.chatroom.common.user.domain.entity.IpInfo;
import com.dazhou.chatroom.common.user.domain.entity.User;
import com.dazhou.chatroom.common.user.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 20:54
 */
@Service
@Slf4j
public class IpServiceImpl implements IpService, DisposableBean {
    private static ExecutorService executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(500), new NamedThreadFactory("refresh-ipDetail", false));

    @Autowired
    private UserDao userDao;
    @Override
    public void refreshIpDetailAsync(Long uid) {
        executor.execute(()->{
            User user = userDao.getById(uid);
            IpInfo ipInfo = user.getIpInfo();
            if (Objects.isNull(ipInfo)){
                return;
            }
            String ip = ipInfo.needRefreshIp();
            if (StringUtils.isBlank(ip)){
                return;
            }
            IpDetail ipDetail =tryGetIpDetailOrNullTreeTimes(ip);
            if(Objects.nonNull(ipDetail)){
                //刷新ip
                ipInfo.refreshIpDetail(ipDetail);
                User update = new User();
                update.setId(uid);
                update.setIpInfo(ipInfo);
                userDao.updateById(update);
            }
        });
    }

    private static IpDetail tryGetIpDetailOrNullTreeTimes(String ip) {
        for (int i = 0; i <3 ; i++) {
            IpDetail ipDetail=getIpDetailOrNull(ip);
            if (Objects.nonNull(ipDetail)){
                return ipDetail;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("获取ip详情失败",e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    //请求淘宝接口 获取ip详情地址
    private static IpDetail getIpDetailOrNull(String ip) {
        //发起请求
        String body = HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
        try {
            //将返回值转换
            ApiResult<IpDetail> result = JSONUtil.toBean(body, new TypeReference<ApiResult<IpDetail>>() {
            }, false);
            if (Objects.nonNull(result)) {
                return result.getData();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
    public static void main(String[] args) {
        Date begin = new Date();
        for (int i = 0; i < 3 ; i++) {
            int finalI = i;
            executor.execute(() -> {
                IpDetail ipDetail = tryGetIpDetailOrNullTreeTimes("192.168.1.103");
                if (Objects.nonNull(ipDetail)) {
                    Date date = new Date();
                    System.out.println(String.format("第%d次成功,目前耗时：%dms", finalI, (date.getTime() - begin.getTime())));
                }
            });
        }
    }

    //优雅停机关闭线程池
    @Override
    public void destroy() throws Exception {
        //这里并不是马上关闭，而是不在接收新的任务
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {//最多等30秒，处理不完就拉倒
            if (log.isErrorEnabled()) {
                log.error("Timed out while waiting for executor [{}] to terminate", executor);
            }
        }
    }
}

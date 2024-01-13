package com.dazhou.chatroom.common.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.dazhou.chatroom.common.common.annotation.RedisssonLock;
import com.dazhou.chatroom.common.common.service.LockService;
import com.dazhou.chatroom.common.common.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**分布式锁切面
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-14 0:38
 */
@Slf4j
@Aspect
@Component
@Order(0) //确保比事务注解先执行，分布式锁在事务外
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(com.dazhou.chatroom.common.common.annotation.RedisssonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedisssonLock redisssonLock = method.getAnnotation(RedisssonLock.class);
        //获取key前缀 方法名+权限名 根据业务的不同可以设置不同的前缀
        String prefix = StrUtil.isBlank(redisssonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redisssonLock.prefixKey();//默认方法限定名+注解排名（可能多个）
        //
        String key=SpElUtils.parseSpEl(method,joinPoint.getArgs(),redisssonLock.key());
        return lockService.executeWithLock(prefix+":"+key, redisssonLock.waitTime(), redisssonLock.unit(),joinPoint::proceed);
    }
}

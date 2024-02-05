package com.dazhou.chatroom.transaction.aspect;

import cn.hutool.core.date.DateUtil;

import com.dazhou.chatroom.common.common.utils.JsonUtils;
import com.dazhou.chatroom.transaction.annotation.SecureInvoke;
import com.dazhou.chatroom.transaction.domain.dto.SecureInvokeDTO;
import com.dazhou.chatroom.transaction.domain.entity.SecureInvokeRecord;
import com.dazhou.chatroom.transaction.service.SecureInvokeHolder;
import com.dazhou.chatroom.transaction.service.SecureInvokeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 安全执行切面
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-04-20
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)//确保最先执行
@Component
public class SecureInvokeAspect {
    @Autowired
    private SecureInvokeService secureInvokeService;

    @Around("@annotation(secureInvoke)")
    public Object around(ProceedingJoinPoint joinPoint, SecureInvoke secureInvoke) throws Throwable {
        boolean async = secureInvoke.async();
        boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        //非事务状态，直接执行，不做任何保证。
        if (SecureInvokeHolder.isInvoking() || !inTransaction) {
            return joinPoint.proceed();
        }
        //获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取参数
        List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());

        SecureInvokeDTO dto = SecureInvokeDTO.builder()
                //设置参数
                .args(JsonUtils.toStr(joinPoint.getArgs()))
                //类名
                .className(method.getDeclaringClass().getName())
                //方法名
                .methodName(method.getName())
                //参数
                .parameterTypes(JsonUtils.toStr(parameters))
                .build();
        //构建表对象
        SecureInvokeRecord record = SecureInvokeRecord.builder()
                .secureInvokeDTO(dto)
                .maxRetryTimes(secureInvoke.maxRetryTimes())
                .nextRetryTime(DateUtil.offsetMinute(new Date(), (int) SecureInvokeService.RETRY_INTERVAL_MINUTES))
                .build();
        //执行一次并保存到数据库 持久化
        secureInvokeService.invoke(record, async);
        return null;
    }
}

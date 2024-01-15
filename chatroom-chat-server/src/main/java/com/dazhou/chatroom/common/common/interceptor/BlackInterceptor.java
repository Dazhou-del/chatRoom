package com.dazhou.chatroom.common.common.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.dazhou.chatroom.common.common.domain.dto.RequestInfo;
import com.dazhou.chatroom.common.common.exception.HttpErrorEnum;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import com.dazhou.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.dazhou.chatroom.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 拉黑用户拦截器
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 22:22
 */
@Order
@Component
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private UserCache userCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取所有拉黑信息
        Map<Integer, Set<String>> blackMap = userCache.getBlackMap();
        RequestInfo requestInfo = RequestHolder.get();
        //判断当前用户的id在不在拉黑名单中
        if (inBlackList(requestInfo.getUid(), blackMap.get(BlackTypeEnum.UIP.getId()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        } //判断当前用户的ip在不在拉黑名单中
        if (inBlackList(requestInfo.getIp(), blackMap.get(BlackTypeEnum.IP.getId()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }
    private boolean inBlackList(Object target, Set<String> blackSet) {
        if (Objects.isNull(target) || CollectionUtil.isEmpty(blackSet)) {
            return false;
        }
        return blackSet.contains(target.toString());
    }



}

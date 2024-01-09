package com.dazhou.chatroom.common.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.dazhou.chatroom.common.common.domain.dto.RequestInfo;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 获取uid ip拦截器
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 22:22
 */
@Order
@Component
public class CollectorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestInfo info = new RequestInfo();
        //获取uid设置到RequestInfo中
        info.setUid(Optional.ofNullable(request.getAttribute(TokenInterceptor.UID)).map(Object::toString).map(Long::parseLong).orElse(null));
        //获取ip设置到RequestInfo中
        info.setIp(ServletUtil.getClientIP(request));
        //将RequestInfo设置到threadLocal中
        RequestHolder.set(info);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }

}

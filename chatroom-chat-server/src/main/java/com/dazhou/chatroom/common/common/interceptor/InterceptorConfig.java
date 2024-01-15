package com.dazhou.chatroom.common.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 让拦截器生效
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-09 21:59
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private CollectorInterceptor collectorInterceptor;
    @Autowired
    private BlackInterceptor blackInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**");   //这里设置拦截器的位置 对应拦截器生效的位置
        registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
        registry.addInterceptor(blackInterceptor)
                .addPathPatterns("/capi/**");
    }
}

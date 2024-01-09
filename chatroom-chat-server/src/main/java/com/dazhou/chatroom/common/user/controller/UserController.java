package com.dazhou.chatroom.common.user.controller;


import cn.hutool.http.HttpRequest;
import com.dazhou.chatroom.common.common.domain.dto.RequestInfo;
import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.common.interceptor.TokenInterceptor;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户相关接口")
public class UserController {
    @GetMapping("/userInfo")
    @ApiOperation("获取用户详细信息")
    public ApiResult<UserInfoResp> getUserInfo(HttpServletRequest request){
        RequestInfo requestInfo = RequestHolder.get();
        System.out.println(requestInfo.getUid());
        System.out.println(requestInfo.getIp());
        return null;
    }
}


package com.dazhou.chatroom.common.user.controller;


import cn.hutool.http.HttpRequest;
import com.dazhou.chatroom.common.common.domain.dto.RequestInfo;
import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.common.interceptor.TokenInterceptor;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import com.dazhou.chatroom.common.user.domain.vo.req.ModifyNameReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.UserInfoResp;
import com.dazhou.chatroom.common.user.service.UserService;
import com.dazhou.chatroom.common.user.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户详细信息")
    public ApiResult<UserInfoResp> getUserInfo(HttpServletRequest request){
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/name")
    @ApiOperation("修改用户名字")
    public ApiResult<UserInfoResp> modifyName(@Valid @RequestBody ModifyNameReq req){
        userService.modifyName(RequestHolder.get().getUid(),req.getName());
        return ApiResult.success();
    }
}


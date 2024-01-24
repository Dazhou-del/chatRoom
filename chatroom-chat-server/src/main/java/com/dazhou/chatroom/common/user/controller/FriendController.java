package com.dazhou.chatroom.common.user.controller;

import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendApplyReq;
import com.dazhou.chatroom.common.user.domain.vo.req.FriendCheckReq;
import com.dazhou.chatroom.common.user.domain.vo.resp.FriendCheckResp;
import com.dazhou.chatroom.common.user.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-23 22:21
 * 好友相关接口
 */
@RestController
@RequestMapping("/capi/user/friend")
@Api(tags = "好友相关接口")
@Slf4j
public class FriendController {
    @Resource
    private FriendService friendService;

    @GetMapping("/check")
    @ApiOperation("批量判断是否是自己好友")
    public ApiResult<FriendCheckResp> check(@Valid FriendCheckReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(friendService.check(uid, request));
    }

    @PostMapping("/apply")
    @ApiOperation("申请好友")
    public ApiResult<Void> apply(@Valid @RequestBody FriendApplyReq request) {
        Long uid = RequestHolder.get().getUid();
        friendService.apply(uid, request);
        return ApiResult.success();
    }

}

package com.dazhou.chatroom.common.chat.controller;

import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessagePageReq;
import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessageReq;
import com.dazhou.chatroom.common.chat.domain.vo.response.ChatMessageResp;
import com.dazhou.chatroom.common.chat.service.ChatService;
import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.common.domain.vo.resp.CursorPageBaseResp;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import com.dazhou.chatroom.common.user.domain.enums.BlackTypeEnum;
import com.dazhou.chatroom.common.user.service.cache.UserCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * 群聊相关接口
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 1:16
 */
@RestController
@RequestMapping("/capi/chat")
@Api(tags = "聊天室相关接口")
@Slf4j
public class ChatController {
    @Resource
    private ChatService chatService;

    @Autowired
    private UserCache userCache;
    private Set<String> getBlackUidSet() {
        return userCache.getBlackMap().getOrDefault(BlackTypeEnum.UIP.getId(), new HashSet<>());
    }

    @GetMapping("/public/msg/page")
    @ApiOperation("消息列表")
//    @FrequencyControl(time = 120, count = 20, target = FrequencyControl.Target.IP)
    public ApiResult<CursorPageBaseResp<ChatMessageResp>> getMsgPage(@Valid ChatMessagePageReq request) {
        CursorPageBaseResp<ChatMessageResp> msgPage = chatService.getMsgPage(request, RequestHolder.get().getUid());
        //过滤掉黑名单消息
        filterBlackMsg(msgPage);
        return ApiResult.success(msgPage);
    }
    private void filterBlackMsg(CursorPageBaseResp<ChatMessageResp> memberPage) {
        //所有被拉黑的uid
        Set<String> blackMembers = getBlackUidSet();
        //如果消息的uid在黑名单则删掉这条消息
        memberPage.getList().removeIf(a -> blackMembers.contains(a.getFromUser().getUid().toString()));
    }

    @PostMapping("/msg")
    @ApiOperation("发送消息")
//    @FrequencyControl(time = 5, count = 3, target = FrequencyControl.Target.UID)
//    @FrequencyControl(time = 30, count = 5, target = FrequencyControl.Target.UID)
//    @FrequencyControl(time = 60, count = 10, target = FrequencyControl.Target.UID)
    public ApiResult<ChatMessageResp> sendMsg(@Valid @RequestBody ChatMessageReq request) {
        Long msgId = chatService.sendMsg(request, RequestHolder.get().getUid());
        //返回完整消息格式，方便前端展示
        return ApiResult.success(chatService.getMsgResp(msgId, RequestHolder.get().getUid()));
    }
}

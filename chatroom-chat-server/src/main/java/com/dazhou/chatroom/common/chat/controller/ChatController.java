package com.dazhou.chatroom.common.chat.controller;

import com.dazhou.chatroom.common.chat.domain.vo.request.ChatMessageReq;
import com.dazhou.chatroom.common.chat.domain.vo.response.ChatMessageResp;
import com.dazhou.chatroom.common.chat.service.ChatService;
import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import com.dazhou.chatroom.common.common.utils.RequestHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

package com.dazhou.chatroom.common.chat.domain.vo.request;

import com.dazhou.chatroom.common.common.domain.vo.req.CursorPageBaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 消息列表请求
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-06 13:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagePageReq extends CursorPageBaseReq {
    @NotNull
    @ApiModelProperty("会话id")
    private Long roomId;
}

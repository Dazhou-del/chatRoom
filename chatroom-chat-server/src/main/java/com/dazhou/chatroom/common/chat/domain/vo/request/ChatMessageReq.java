package com.dazhou.chatroom.common.chat.domain.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 聊天信息点播
 *消息发送请求体
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-04 1:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReq {
    @NotNull
    @ApiModelProperty("房间id")
    private Long roomId;

    @ApiModelProperty("消息类型")
    @NotNull
    private Integer msgType;

    /**
     * @see com.dazhou.chatroom.common.chat.domain.entity.msg
     */
    @ApiModelProperty("消息内容，类型不同传值不同，见https://www.yuque.com/snab/mallcaht/rkb2uz5k1qqdmcmd")
    @NotNull
    private Object body;

}

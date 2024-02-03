package com.dazhou.chatroom.common.chat.domain.entity.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 表情图片消息入参
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-03 23:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmojisMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("下载地址")
    @NotBlank
    private String url;
}
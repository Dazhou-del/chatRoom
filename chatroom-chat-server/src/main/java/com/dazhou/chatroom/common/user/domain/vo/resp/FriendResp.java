package com.dazhou.chatroom.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友校验
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResp {

    @ApiModelProperty("好友uid")
    private Long uid;
    /**
     * @see com.dazhou.chatroom.common.user.domain.enums.ChatActiveStatusEnum
     */
    @ApiModelProperty("在线状态 1在线 2离线")
    private Integer activeStatus;
}

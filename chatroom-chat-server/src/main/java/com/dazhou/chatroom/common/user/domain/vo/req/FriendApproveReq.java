package com.dazhou.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 申请好友信息
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-24 22:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApproveReq {

    @NotNull
    @ApiModelProperty("申请id")
    private Long applyId;

}

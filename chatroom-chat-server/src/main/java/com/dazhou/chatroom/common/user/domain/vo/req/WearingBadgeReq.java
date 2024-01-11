package com.dazhou.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:39
 */
@Data
public class WearingBadgeReq {
    @ApiModelProperty(value = "徽章id")
    @NotNull
    private Long itemId;
}

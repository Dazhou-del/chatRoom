package com.dazhou.chatroom.common.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-02-01 23:41
 */
@Data
public class IpResult<T> implements Serializable {
    @ApiModelProperty("错误码")
    private Integer code;
    @ApiModelProperty("错误消息")
    private String msg;
    @ApiModelProperty("返回对象")
    private T data;

    public boolean isSuccess() {
        return Objects.nonNull(this.code) && this.code == 0;
    }
}

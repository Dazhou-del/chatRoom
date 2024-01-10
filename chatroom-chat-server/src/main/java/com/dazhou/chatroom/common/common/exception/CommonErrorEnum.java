package com.dazhou.chatroom.common.common.exception;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:55
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {

    PARAM_INVALID(-2,"参数校验失败"),
    BUSINESS_CODE(0,"参数校验失败"),
    SYSTEM_ERROR(-1,"系统出小差了");

    private final Integer code;

    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}

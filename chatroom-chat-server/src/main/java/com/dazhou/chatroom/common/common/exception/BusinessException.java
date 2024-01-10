package com.dazhou.chatroom.common.common.exception;

import lombok.Data;

/**
 * 业务异常类
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 22:23
 */
@Data
public class BusinessException extends RuntimeException{

    protected Integer errorCode;

    protected String errorMsg;

    public BusinessException(String errorMsg){
        super(errorMsg);
        this.errorCode=CommonErrorEnum.BUSINESS_CODE.getErrorCode();
        this.errorMsg=errorMsg;
    }
    public BusinessException(Integer errorCode,String errorMsg){
        super(errorMsg);
        this.errorMsg=errorMsg;
        this.errorCode=errorCode;
    }
}

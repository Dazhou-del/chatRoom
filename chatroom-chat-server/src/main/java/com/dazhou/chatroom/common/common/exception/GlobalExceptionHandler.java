package com.dazhou.chatroom.common.common.exception;

import com.dazhou.chatroom.common.common.domain.vo.resp.ApiResult;
import javafx.beans.binding.StringBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2024-01-10 21:48
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    public ApiResult<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(x->errorMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
        String errorMsgString = errorMsg.toString();
        return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getCode(),errorMsgString.substring(0,errorMsgString.length()-1)); //移除逗号
    }

    /**
     * 最后一道防线
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> Throwable(Throwable e){
        log.error("system exception! The reason is:{}",e.getMessage());
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }

    /**
     * 业务异常
     * @param businessException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException businessException){
        log.info("system exception! The reason is:{}",businessException.getMessage());
        return ApiResult.fail(businessException.errorCode, businessException.errorMsg);
    }
}

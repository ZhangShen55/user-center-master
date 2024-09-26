package com.chanson.usercenterbackend.exception;


import com.chanson.usercenterbackend.common.BaseResponse;
import com.chanson.usercenterbackend.common.ErrorCode;
import com.chanson.usercenterbackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * RuntimeException 全局处理
     * @param runtimeException
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandle(RuntimeException runtimeException) {
        log.error("runtimeException:" + runtimeException.getMessage(), runtimeException);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, runtimeException.getMessage(), "");
    }

    /**
     * BaseException 全局处理
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse baseExceptionHandle(BusinessException businessException) {
        log.error("BusinessException :" + businessException.getMessage(), businessException);
        return ResultUtils.error(businessException.getCode(), businessException.getMessage(), businessException.getDescription());
    }
}

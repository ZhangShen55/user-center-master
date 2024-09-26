package com.chanson.usercenterbackend.exception;


import com.chanson.usercenterbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private final int code;
    private final String description;

    public BaseException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BaseException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        //指定输入错误描述
        this.description = description;
    }
}

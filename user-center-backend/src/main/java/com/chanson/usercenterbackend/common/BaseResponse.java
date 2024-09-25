package com.chanson.usercenterbackend.common;

import java.io.Serializable;


/**
 * 通用返回类
 *
 * @param <T>
 * @author zhangshen
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 5911240676465101596L;
    private int code;
    private T Data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        Data = data;
        this.message = message;
        this.description = description;
    }


    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, T data) {
        this(errorCode.getCode(), data, errorCode.getMessage(), errorCode.getDescription());
    }


}

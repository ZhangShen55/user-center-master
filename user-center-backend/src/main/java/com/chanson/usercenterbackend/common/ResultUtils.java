package com.chanson.usercenterbackend.common;


/**
 * 通用返回工具类
 * @author zhangshen
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(ErrorCode.SUCCESS.getCode(),data,ErrorCode.SUCCESS.getMessage(),ErrorCode.SUCCESS.getDescription());
    }

    public static <T> BaseResponse<T> error(T data){
        return new BaseResponse<T>(ErrorCode.SUCCESS.getCode(),null,ErrorCode.SUCCESS.getMessage(),ErrorCode.SUCCESS.getDescription());
    }
}

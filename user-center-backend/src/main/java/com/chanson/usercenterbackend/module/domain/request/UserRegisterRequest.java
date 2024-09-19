package com.chanson.usercenterbackend.module.domain.request;


import lombok.Data;


/**
 * 用户注册包装类
 * @author ZhangShen
 */
@Data
public class UserRegisterRequest {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}

package com.chanson.usercenterbackend.module.domain.request;


import lombok.Data;




/**
 * 用户登录包装类
 * @author ZhangShen
 */
@Data
public class UserLoginRequest {
    private String userAccount;
    private String userPassword;
}

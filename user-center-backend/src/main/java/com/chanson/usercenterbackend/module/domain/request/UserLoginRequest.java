package com.chanson.usercenterbackend.module.domain.request;


import lombok.Data;

import java.io.Serializable;


/**
 * 用户登录包装类
 * @author ZhangShen
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 9165229045923385718L;
    private String userAccount;
    private String userPassword;


}

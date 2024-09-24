package com.chanson.usercenterbackend.module.domain.request;


import lombok.Data;

import java.io.Serializable;


/**
 * 用户注册包装类
 * @author ZhangShen
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 8717290050972404879L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String plantCode;
}

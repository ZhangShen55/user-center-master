package com.chanson.usercenterbackend.service;

import com.chanson.usercenterbackend.module.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author sencheung
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-18 17:18:22
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param password 用户密码
     * @param checkPassword 校验密码
     * @return
     */
    long userRegister(String userAccount ,String password,String checkPassword);
}

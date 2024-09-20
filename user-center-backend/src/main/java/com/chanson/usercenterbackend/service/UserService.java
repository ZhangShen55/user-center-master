package com.chanson.usercenterbackend.service;

import com.chanson.usercenterbackend.module.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author sencheung
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-18 17:18:22
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return
     */
    long userRegister(String userAccount ,String userPassword,String checkPassword);


    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 用户密码
     * @return 登录用户
     */
     User userLogin(String userAccount , String userPassword,HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 脱敏用户对象
     * @return 脱敏用户
     */
    User getSafteUser(User originUser);
}

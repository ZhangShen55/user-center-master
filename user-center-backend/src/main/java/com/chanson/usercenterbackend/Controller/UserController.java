package com.chanson.usercenterbackend.Controller;


import com.chanson.usercenterbackend.module.domain.User;
import com.chanson.usercenterbackend.module.domain.request.UserLoginRequest;
import com.chanson.usercenterbackend.module.domain.request.UserRegisterRequest;
import com.chanson.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        //Service层已经进行了判空，为什么在controller还要进行判空呢？
        //Controller只倾向于参数本身的校验，不设计逻辑本身
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        long userId = userService.userRegister(userAccount, userPassword, checkPassword);
        return userId;
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //Service层已经进行了判空，为什么在controller还要进行判空呢？
        //Controller只倾向于参数本身的校验，不设计逻辑本身
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User user = userService.userLogin(userAccount,userPassword,request);
        return user;
    }

}

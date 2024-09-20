package com.chanson.usercenterbackend.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chanson.usercenterbackend.constant.UserConstant;
import com.chanson.usercenterbackend.module.domain.User;
import com.chanson.usercenterbackend.module.domain.request.UserLoginRequest;
import com.chanson.usercenterbackend.module.domain.request.UserRegisterRequest;
import com.chanson.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chanson.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

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

    @GetMapping("/search")
    public List<User> searchUser(String username,HttpServletRequest httpServletRequest){
        //鉴权 仅管理员可查询
        if(!isAdmin(httpServletRequest)){
            //鉴权不通过 返回空列表
            return new ArrayList<>();
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            //进行查询
            userQueryWrapper.like("username",username);
        }
        // 返回结果 脱敏用户列表
        return userService.list(userQueryWrapper).stream().map(user -> userService.getSafteUser(user)
        ).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public  boolean deleteUser(@RequestBody long id,HttpServletRequest httpServletRequest){
        // 鉴权仅管理员可查询
        if(!isAdmin(httpServletRequest)){
            return false;
        }
        if(id <= 0){
            return false;
        }
        return  userService.removeById(id);
    }


    /**
     * 判断登录用户是否为管理员
     * @param httpServletRequest
     * @return boolean
     */
    private boolean isAdmin(HttpServletRequest httpServletRequest){
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObj;
        return user != null && user.getUserRole() == 1;
    }
}

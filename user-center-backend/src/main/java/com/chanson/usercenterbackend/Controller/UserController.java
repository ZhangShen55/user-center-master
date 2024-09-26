package com.chanson.usercenterbackend.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chanson.usercenterbackend.common.BaseResponse;
import com.chanson.usercenterbackend.common.ErrorCode;
import com.chanson.usercenterbackend.common.ResultUtils;
import com.chanson.usercenterbackend.exception.BusinessException;
import com.chanson.usercenterbackend.module.domain.User;
import com.chanson.usercenterbackend.module.domain.request.UserLoginRequest;
import com.chanson.usercenterbackend.module.domain.request.UserRegisterRequest;
import com.chanson.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.chanson.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String plantCode = userRegisterRequest.getPlantCode();
        //Service层已经进行了判空，为什么在controller还要进行判空呢？
        //Controller只倾向于参数本身的校验，不设计逻辑本身
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,plantCode)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"注册有参数为空");
        }
        long userId = userService.userRegister(userAccount, userPassword, checkPassword, plantCode);
        return ResultUtils.success(userId);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //Service层已经进行了判空，为什么在controller还要进行判空呢？
        //Controller只倾向于参数本身的校验，不设计逻辑本身
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }


    /**
     * 用户注销登录
     * @param request
     * @return 注销成功
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 获取当前user（获取当前用户态）
     * @param httpServletRequest request
     * @return 当前用户
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest httpServletRequest){
        //获取到当前User
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User)userObj;
        //这里不能直接返回 
        //直接返回只是之前存入的用户状态 如果用户付费更新了其他数据呢
        //所以要再查一遍
        User user = userService.getById(currentUser.getId());
        // todo 查询用户状态（是否是封号状态）
        User safteUser = userService.getSafteUser(user);
        return ResultUtils.success(safteUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username,HttpServletRequest httpServletRequest){
        //鉴权 仅管理员可查询
        if(!isAdmin(httpServletRequest)){
            // 鉴权不通过抛出异常
            throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            //进行查询
            userQueryWrapper.like("username",username);
        }
        // 返回结果 脱敏用户列表
        List<User> userList = userService.list(userQueryWrapper).stream().map(user -> userService.getSafteUser(user)
        ).collect(Collectors.toList());
        return ResultUtils.success(userList);
    }

    @PostMapping("/delete")
    public  BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest httpServletRequest){
        // 鉴权仅管理员可查询
        if(!isAdmin(httpServletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTH,"用户无权限");
        }
        if(id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
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

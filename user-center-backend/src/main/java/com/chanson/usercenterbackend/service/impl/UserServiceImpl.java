package com.chanson.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanson.usercenterbackend.module.domain.User;
import com.chanson.usercenterbackend.service.UserService;
import com.chanson.usercenterbackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chanson.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author sencheung
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-09-18 17:18:22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SLAT = "chanson";

    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            return -1;
        }
        //账户不能包含特殊字符  在此之前进行账户名重复校验 可能会造成资源浪费
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{6,8}$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //账户名不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            return -1;
        }
        // 2.密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SLAT + userPassword).getBytes());

        //用户插入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);

        //防止数据库未能生成Id -> null 拆箱失败
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        //账户不能包含特殊字符  在此之前进行账户名重复校验 可能会造成资源浪费
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{6,8}$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        // 2.密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SLAT + userPassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount",userAccount);
        userQueryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user==null){
            //登录失败 打印日志
            log.info("user login failed,userAccount cannot match password!");
            return null;
        }

        // 3.用户脱敏
        User safteUser = getSafteUser(user);
        // 4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safteUser);

        return safteUser;
    }


    @Override
    public User getSafteUser(User originUser){
        if(originUser == null){
            return null;
        }
        User safteUser = new User();
        safteUser.setId(originUser.getId());
        safteUser.setUsername(originUser.getUsername());
        safteUser.setUserAccount(originUser.getUserAccount());
        safteUser.setAvatarUrl(originUser.getAvatarUrl());
        safteUser.setGender(originUser.getGender());
        safteUser.setPhone(originUser.getPhone());
        safteUser.setEmail(originUser.getEmail());
        safteUser.setUserStatus(originUser.getUserStatus());
        safteUser.setUserRole(originUser.getUserRole());
        safteUser.setCreateTime(originUser.getCreateTime());
        safteUser.setUpdateTime(originUser.getUpdateTime());
        return safteUser;
    }
}





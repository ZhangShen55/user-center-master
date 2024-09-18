package com.chanson.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanson.usercenterbackend.domain.User;
import com.chanson.usercenterbackend.mapper.UserMapper;
import com.chanson.usercenterbackend.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author sencheung
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-18 15:55:15
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}





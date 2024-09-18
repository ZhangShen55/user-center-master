package com.chanson.usercenterbackend.service;
import java.util.Date;

import com.chanson.usercenterbackend.module.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
public  class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userService;


    @Test
    public void testAddUser() {
        User user1 = new User();
        user1.setId(0L);
        user1.setUsername("zhangshen");
        user1.setUserAccount("110");
        user1.setAvatarUrl("https://blog.csdn.net/weixin_44519169?spm=1000.2115.3001.5343");
        user1.setGender(1);
        user1.setUserPassword("12345678");
        user1.setPhone("12312341234");
        user1.setEmail("12312341234@qq.com");
        user1.setUserStatus(0);
        user1.setCreateTime(new Date());
        user1.setUpdateTime(new Date());
        user1.setIsDelete(0);
        user1.setUserRole(0);
        boolean result = userService.save(user1);
        Assertions.assertTrue(result); // 断言结果是否成功



    }

    @Test
    public void userRegister() {
        long userId = userService.userRegister("1111", "12345678", "12345678");
        //Assertions.assertEquals(-1,userId);
        System.out.println(userId);
    }

    @Test
    public void doLogin() {
        String userAccount = "1111";
        String password = "12345678";
        User user = userService.doLogin(userAccount, password);
        System.out.println(user.getId());
    }
}
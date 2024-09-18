package com.chanson.usercenterbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录账号
     */
    @TableField(value = "userAccount")
    private String userAccount;

    /**
     * 性别1男0女
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 密码
     */
    @TableField(value = "userPassword")
    private String userPassword;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户状态 0-正常
     */
    @TableField(value = "userStatus")
    private Integer userStatus;

    /**
     * 创建时间（数据插入时间）
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间(数据更新时间)
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 是否删除(逻辑删除) 0- 未删除 1 - 删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;

    /**
     * userRole 用户角色 0 - 普通用户 1 - 管理员
     */
    @TableField(value = "userRole")
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
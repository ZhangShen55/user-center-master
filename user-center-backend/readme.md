

```sql caption="用户表"
-- auto-generated definition

drop  table user;

create table user
(
    id           bigint auto_increment comment '主键'
        primary key,
    username     varchar(256)                       not null comment '昵称',
    userAccount  varchar(256)                       not null comment '登录账号',
    gender       tinyint                            null comment '性别1男0女',
    userPassword varchar(256)                       not null comment '密码',
    phone        varchar(256)                       null comment '电话',
    email        varchar(256)                       null comment '邮箱',
    userStatus   int      default 0                 null comment '用户状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间（数据插入时间）',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间(数据更新时间)',
    isDelete     tinyint  default 0                 null comment '是否删除(逻辑删除) 0- 未删除 1 - 删除',
    userRole     tinyint  default 0                 null comment 'userRole 用户角色 0 - 普通用户 1 - 管理员'
);


```
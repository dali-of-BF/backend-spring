-- auto-generated definition
create table sys_account
(
    id               varchar(36)          not null
        primary key,
    username         varchar(50)          not null comment '用户名',
    phone            varchar(11)          not null comment '电话',
    password         varchar(500)         null comment '密码',
    deleted          tinyint(1) default 0 null comment '假删除标志位',
    created_by       varchar(50)          null comment '创建者',
    last_modify_by   varchar(50)          null comment '最后修改者',
    last_modify_date datetime             null comment '最后修改时间',
    gender           tinyint(1) default 1 null comment '性别 男1女0',
    status           tinyint(1) default 1 null comment '状态 0关1开',
    id_card          varchar(18)          null comment '身份证号码',
    created_date     datetime             null comment '创建时间'
)
    comment '系统登录表';


-- auto-generated definition
create table sys_user
(
    id               varchar(36)          not null
        primary key,
    name             varchar(36)          not null comment '用户名',
    gender           tinyint(1) default 1 null comment '性别 男1女0',
    status           tinyint(1) default 1 null comment '状态 1正常0禁用',
    created_by       varchar(50)          null comment '创建者',
    created_date     datetime             null comment '创建时间',
    last_modify_by   varchar(50)          null comment '修改者',
    last_modify_date datetime             null comment '最后修改时间',
    age              int                  null comment '年龄'
)
    comment '用户表';


create table if not exists `message` (
  `id`              bigint        not null  auto_increment comment '主键',
  `sender`          varchar(50)   not null comment '发送方ID',
  `receiver`        varchar(50)   not null comment '接收方ID',
  `identification`  varchar(101)  not null comment '发送方ID|接收方ID',
  `type`            tinyint       not null comment '消息类型',
  `status`          tinyint       not null comment '消息状态: 0->初始，1->已投递，2->已读',
  `content`         varchar(255)  not null comment '消息内容',
  `created_time`    datetime      not null default current_timestamp comment '创建时间',
  `read_time`       datetime      not null default current_timestamp comment '已读时间',
  `msg_id`          char(36)      not null comment '客户端消息唯一ID',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='消息表';


create table if not exists `user` (
  `id`              bigint        not null  auto_increment comment '主键',
  `login_id`        varchar(50)   not null comment '账号',
  `user_name`       varchar(50)   not null comment '客户姓名',
  `password`        varchar(128)  not null comment '密码',
  `icon`            varchar(50)   not null comment '头像地址',
  primary key (`id`),
  unique key (`login_id`)
) engine=innodb default charset=utf8mb4 comment='用户表';

insert into `user`(`login_id`, `user_name`, `password`, `icon`) values
('oyl', '欧阳亮', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'oyl.jpg'),
('A1019088', '周啸天', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'A1019088.jpg'),
('Y0010081', '杨能慧', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'Y0010081.jpg'),
('Y0010715', '陶红', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'Y0010715.jpg'),
('Y0009521', '李珊', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'Y0009521.jpg'),
('A1018737', '刘岩', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'A1018737.jpg'),
('Y0011983', '王川港', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'Y0011983.jpg'),
('Y0010099', '陈华应', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'Y0010099.jpg'),
('A1019595', '刘清', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'A1019595.jpg'),
('A1019752', '赵顺', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'A1019752.jpg'),
('A1018578', '王涛', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', 'A1018578.jpg');



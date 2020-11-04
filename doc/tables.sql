create table if not exists `message` (
  `id`              bigint        not null  auto_increment comment '主键',
  `sender`          varchar(50)   not null comment '发送方ID',
  `receiver`        varchar(50)   not null comment '接收方ID',
  `type`            tinyint       not null comment '消息类型',
  `status`          tinyint       not null comment '消息状态: 0->初始，1->已投递，2->已读',
  `content`         varchar(255)  not null comment '消息内容',
  `created_time`    datetime      not null default current_timestamp comment '创建时间',
  `read_time`       datetime      not null default current_timestamp comment '已读时间',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='消息表';

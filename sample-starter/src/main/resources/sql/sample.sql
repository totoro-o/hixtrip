#todo 你的建表语句,包含索引

-- 根据订单创建日期分库分表
CREATE DATABASE IF NOT EXISTS sys_order_2023;

USE sys_order_2023;

create table if not exists `sys_order_2023_01`
(
    `id`          varchar(20)    not null comment '订单号',
    `buyer_id`    varchar(20)    not null default '' comment '购买人',
    `seller_id`   varchar(20)    not null default '' comment '商户',
    `sku_id`      varchar(20)    not null default '' comment 'skuId',
    `amount`      varchar(20)    not null default '' comment '购买数量',
    `money`       decimal(10, 2) not null default '0.00' comment '购买金额',
    `pay_time`    datetime       not null default current_timestamp comment '购买时间',
    `pay_status`  tinyint(1)     not null default '0' comment '支付状态',
    `del_flag`    tinyint(1)     not null default '0' comment '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(20)    not null default '' comment '创建者',
    `create_time` datetime       not null default current_timestamp comment '创建时间',
    `update_by`   varchar(20)    not null default '' comment '更新者',
    `update_time` datetime       not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`),
    index idx_buyerId_createTime (buyer_id, create_time),
    index idx_sellerId_createTime (seller_id, create_time)
) engine = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT = '订单表';



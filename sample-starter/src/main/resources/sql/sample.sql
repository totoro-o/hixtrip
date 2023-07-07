# todo 你的建表语句,包含索引
create table `order`
(
    id                        bigint                              not null comment '主键'primary key,
    order_number                  varchar(32)                         not null comment '订单号',
    user_id                   varchar(32)                         not null comment '用户id',
    sku_id                    varchar(32)                         not null comment 'skuid',
    sku_price                 decimal(10, 2)                      not null comment 'sku单价',
    sku_num                       bigint                              not null comment '订单数量',
    pay_way                   varchar(32)                         not null comment '支付方式',
    total_price              decimal(10, 2)                      not null comment '支付金额',
    pay_total_price          decimal(10, 2)                      not null comment '实际支付金额',
    order_status                varchar(32)                         not null comment '订单状态',
    pay_time                  timestamp                           not null comment '支付时间',
    del_flag                  int       default 0                 not null comment '删除标记',
    create_time               timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
)comment '订单表';

# 订单表索引
create index idx_order_number
    on `order` (order_number);
create index idx_user
    on `order` (user_id);
#使用订单号作为分区键,在业务中最多的是通过订单号查询数据,
  如果有需要通过用户id查询其订单的,可以创建一个用户id与订单号对应的表,这个表以用户id为分区键,通过用户id查询订单号,再通过订单号查询订单数据

create table `inventory`
(
    id                        bigint                              not null comment '主键'primary key,
    sku_id                    varchar(32)                         not null comment 'skuid',
    sellable_quantity         bigint                      not null comment '可售库存',
    withholding_quantity      bigint                              not null comment '预占库存',
    occupied_quantity         bigint                       not null comment '占用库存'
)comment '库存表';
# 库存表索引
create index idx_sku_id
    on `order` (sku_id);
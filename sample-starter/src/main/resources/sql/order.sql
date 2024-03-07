create table db_order
(
    id          int auto_increment
        primary key,
    user_id     varchar(48)          not null comment '购买人',
    sku_id      varchar(48)          not null comment 'SkuId',
    amount      int                  not null comment '购买数量',
    money       decimal(10, 2)       not null comment '购买金额',
    pay_time    datetime             null comment '购买时间',
    pay_status  varchar(48)          not null comment '支付状态',
    del_flag    tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(256)         null comment '创建人',
    create_time datetime             null comment '创建时间',
    update_by   varchar(256)         null comment '创建人',
    update_time datetime             null comment '修改时间'
)
    comment '订单信息' engine = MRG_MYISAM
                       collate = utf8mb4_general_ci;

create index id
    on db_order (id);

create table db_order_1
(
    id          int auto_increment
        primary key,
    user_id     varchar(48)          not null comment '购买人',
    sku_id      varchar(48)          not null comment 'SkuId',
    amount      int                  not null comment '购买数量',
    money       decimal(10, 2)       not null comment '购买金额',
    pay_time    datetime             null comment '购买时间',
    pay_status  varchar(48)          not null comment '支付状态',
    del_flag    tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(256)         null comment '创建人',
    create_time datetime             null comment '创建时间',
    update_by   varchar(256)         null comment '创建人',
    update_time datetime             null comment '修改时间'
)
    comment '订单信息' engine = MyISAM
                       collate = utf8mb4_general_ci;

create index id
    on db_order_1 (id);

create table db_order_2
(
    id          int auto_increment
        primary key,
    user_id     varchar(48)          not null comment '购买人',
    sku_id      varchar(48)          not null comment 'SkuId',
    amount      int                  not null comment '购买数量',
    money       decimal(10, 2)       not null comment '购买金额',
    pay_time    datetime             null comment '购买时间',
    pay_status  varchar(48)          not null comment '支付状态',
    del_flag    tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(256)         null comment '创建人',
    create_time datetime             null comment '创建时间',
    update_by   varchar(256)         null comment '创建人',
    update_time datetime             null comment '修改时间'
)
    comment '订单信息' engine = MyISAM
                       collate = utf8mb4_general_ci;

create index id
    on db_order_2 (id);


# 还需要实现mybatis对于sql执行前的监听，从代码上做取模分表逻辑


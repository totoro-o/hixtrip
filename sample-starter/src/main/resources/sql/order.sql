create table `order`
(
    id                        bigint                              not null comment '主键'
        primary key,
    order_no                  varchar(32)                         not null comment '订单号',
    user_id                   varchar(32)                         not null comment '用户id',
    sku_id                    varchar(32)                         not null,
    num                       bigint                              not null comment '订单数量',
    sku_price                 decimal(10, 2)                      not null comment 'sku单价',
    total_amount              decimal(10, 2)                      not null comment '总金额',
    pay_status                varchar(32)                         not null comment '支付状态',
    pay_time                  timestamp                           not null comment '支付时间',
    pay_url                   varchar(256)                        not null comment '支付链接',
    pay_amount                varchar(256)                        not null comment '支付金额',
    third_party_serial_number varchar(64)                         not null comment '第三方支付流水号',
    del_flag                  int       default 0                 not null comment '删除标记',
    create_by                 varchar(32)                         not null comment '创建人',
    create_time               timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by                 varchar(32)                         not null comment '最后更新人',
    update_time               timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '订单表';

create index idx_order_no
    on `order` (order_no);

create index idx_sku
    on `order` (sku_id);

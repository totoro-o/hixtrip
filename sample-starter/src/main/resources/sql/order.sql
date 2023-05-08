# 对于订单分库分表设计的构想：
# 为了应对买家、卖家的高频查询，可以将一个订单存储到不同的库表中，以空间来换取时间。
# 也就是说根据买家id进行hash后存放到买家库对应的表，如果买家库也是多库，也是通过买家id进行hash取余来确定数据最终落到哪个库；
# 卖家库也是同理，将卖家id作为分库分表键。当然也少不了根据订单号查询，可以根据订单号再分库分表，当然也可以将订单同步到es，
# 通过订单号中的日期进行数据检索，使用es也可以实现时间范围查询。
# 对于历史订单，划定一个时间节点进行归档处理，可以归档到es中。归档后可以减轻业务库的负担。

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

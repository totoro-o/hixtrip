create table inventory
(
    id                   bigint                              not null comment '主键'
        primary key,
    sku_id               varchar(32)                         not null,
    total_quantity       bigint    default 0                 not null comment '总库存',
    sellable_quantity    bigint    default 0                 not null comment '可售库存',
    withholding_quantity bigint    default 0                 not null comment '预占库存',
    occupied_quantity    bigint    default 0                 not null comment '占用库存',
    del_flag             int       default 0                 not null comment '删除标记',
    create_by            varchar(32)                         not null comment '创建人',
    create_time          timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by            varchar(32)                         not null comment '最后更新人',
    update_time          timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint unique_sku_id
        unique (sku_id)
)
    comment '库存表';

#todo 你的建表语句,包含索引
create table order_primary
(
    order_id         bigint                    not null comment '订单ID'
        primary key,
    user_id          varchar(32)               null comment '用户ID',
    seller_id        varchar(32)               null comment '卖家ID',
    pay_time         datetime                  null comment '支付时间',
    sku_id           char(32)                  not null comment '商品ID',
    amount           int                       not null comment '商品件数',
    money            decimal(12, 4)            not null comment '订单金额',
    order_status     varchar(8) default 'open' not null comment '订单交易状态',
    pay_status       varchar(2) default 'n'    not null comment '支付状态',
    out_trade_pay_no char(21)                  null comment '外部支付订单',
    description      varchar(128)              null comment '备注信息',
    create_by        char(32)                  not null comment '创建人',
    update_by        char(32)                  not null comment '修改人',
    create_time      datetime                  not null comment '创建时间',
    update_time      datetime                  not null comment '修改时间',
    del_flag         bit        default b'0'   not null comment '删除状态'
)
    comment '订单主表';

create index order_primary_user_id_create_time_index
    on order_primary (user_id, create_time);





create table order_seller
(
    order_id       char(20)         not null comment '订单ID'
        primary key,
    seller_id      char(32)         not null comment '卖家ID',
    user_id        char(32)         not null comment '买家ID',
    sku_id         char(32)         not null comment '商品ID',
    amount         int              not null comment '购买数量',
    money          decimal(12, 4)   not null comment '订单金额',
    location_code  char(32)         null comment '位置编码',
    address_detail varchar(256)     null comment '买家地址',
    description    varchar(128)     null comment '卖家备注',
    create_time    datetime         not null comment '创建时间',
    update_time    datetime         not null comment '更新时间',
    create_by      char(32)         not null comment '创建人',
    update_by      char(32)         not null comment '修改人',
    order_status   varchar(8)       not null comment '订单状态',
    pay_status     varchar(8)       not null comment '支付状态',
    del_flag       bit default b'0' not null comment '删除标记'
)
    comment '订单卖家表';

create index order_seller_seller_id_create_time_index
    on order_seller (seller_id, create_time)
    comment '卖家ID索引';

# 买家高并发，核心思路是减轻买家刷订单的压力，尽可能的对买家表进行瘦身，减轻索引重建带来的压力，通过合理的分库分表设计，简化sql跨库查询的复杂度，尽量避免跨库查询。
# 基础设施选型：Mysql、MongoDB、Redis、RocketMQ、Sharding-Sphere、HBase

# 这里订单表设计为订单主表和订单卖家表，还可以继续细分，根据具体的业务对细化订单主表，或拆分订单主表结构。
# 要保证高峰期100+QPS的订单首先在表结构设计上就不允许有过多复杂设计，主订单表需要保留必要数据，不必要数据存入附表，由于用户侧对实时性要求较高，这里采用直接查询的方式来架构；
# 在数据库架构设计上，主数据库采用Mysql存放热数据，采用分库分表形式进行，用户订单表采用订单尾号进行分库，由于订单号生成规则在末尾增加了用户ID的后缀，可稳定让同一用户的订单落到同一个库中去，
# 分表逻辑采用时间分片，根据实际情况按年或按月季度进行划分。建议在mysql中保留半年的订单数据，超出时间的数据，可由异步任务同步到HBase或其他大数据平台中。
# 在合理的DDL结构和足够的分库上直接查询sql的效率不会降低，且可以为用户的订单同步添加缓存，来进一步提高性能。

# 为了分担来自卖家查询的压力，这里参考淘宝，采用订单表拆分的方法，卖家与买家表基本信息一致，多记录一些卖家相关的信息，例如买家地址，联系方式等。这里卖家数据也可和买家数据架构一致，
# 保留3个月数据，分库采用卖家ID进行落库，分表可用时间进行分表。由于卖家查询QPS没有买家的苛刻，这里可以缩减数据库集群来降低成本。
# 再由于卖家允许秒级的延迟，卖家数据可采用异步写入的形式将主表订单状态同步到卖家表中，异步可使用MQ进行处理。

# 针对客服的查询与处理，这里采用MongoDB来存储客服处理的订单数据，MongoDB对于支持海量的数据存储有天然优势，并且扩容容易，写入与读取性能均衡。
# 客服侧并发量少，对于数据延迟的容忍度较高，但要求查询的数据跨度较大，且有相对复杂的查询流程。这里采用异步同步的形式，将订单主表的数据落到客服数据库中，并且客服数据库滚动移除半年前的数据，可定期执行。

# 针对业务团队的数据分析需求，这里需要引入列数据库，由于列数据库对海量数据支持较好，10亿级别的保留数据均可存入hbase中，且列数据库对数据分析等聚合行为支持极佳，对数据分析团队也较为友好。
# 这里使用异步任务，定期将mysql主表数据写入hbase中，供给数据分析团队使用。

# 以上就是我对整体订单高并发数据持久层的基本设计结构




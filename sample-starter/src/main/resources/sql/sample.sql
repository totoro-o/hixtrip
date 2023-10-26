create table `order`
(
    id     varchar(32) not null
        primary key comment '订单id',
    user_id    varchar(32) comment '购买人' not null,
    seller_id  varchar(32) comment '卖家id' not null,
    sku_id     varchar(32) comment 'sku_id' not null,
    amount     int     comment '购买数量' not  null,
    money      decimal(10, 2) comment '购买金额' not null,
    pay_time   datetime    null,
    pay_status varchar(32) comment '支付状态: 0 未支持 1 已支付' not null,
    del_flag   int  default 0 comment '删除标志（0代表存在 1代表删除）'not null,
    create_by  varchar(32) comment '创建人' null,
    create_time datetime  comment '创建时间'  null,
    update_by  varchar(32) comment '修改人' null,
    update_time datetime  comment '修改时间'  null
);

# 围绕订单，给出相关存储设计。 (库存、商品等无需考虑)
# 背景: 存量订单10亿, 日订单增长百万量级。
# 由于订单存量巨大，需要对旧的订单记录进行归档，将一些不常用的旧数据迁移到冷存储中，以减少主库的压力。
# 对于 del_flag = 1的数据，可以定期清理，减轻存储压力。
# 冷数据存储可以使用分布式数据库，如 MongoDB, 使用用户id作为分片键，将数据进行水平切分，避免单点压力过大。

# 主查询场景如下:
# 1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
# > 建立买家id索引。对于高并发的查询，可以采用缓存的方式，将查询结果缓存至redis中，以降低数据库的压力。
create index idx_order_user_id on `order` (user_id);
# 2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
# > 建立卖家id索引, 对于允许秒级延迟的查询，可以在创建单独的读库，采用主从异步复制，通过负载均衡的方式，将卖家查询订单的请求分发至读库，以降低主库的压力。
create index idx_order_seller_id on `order` (seller_id);
# 3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
# > 可以创建一张全局的订单搜索表，包含订单的各种信息（订单号、买家姓名等），并对订单号、买家姓名等字段建立全文索引，通过全文检索工具（如ES 来实现订单的高效搜索。
# 4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
# >  基于订单表建立数据仓库，通过数据仓库的技术（如Hadoop、Spark等）进行大数据分析。同时，利用任务调度每日定时抽取订单数据到数据仓库，以满足定期的数据分析。
# resources/sql中给出整体设计方案。包含存储基础设施选型, DDL（基础字段即可）, 满足上述场景设计思路。
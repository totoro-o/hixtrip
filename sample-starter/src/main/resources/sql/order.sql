-- 订单表
CREATE TABLE `order`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '订单号',
    `user_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '购买人',
    `seller_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '卖家id',
    `sku_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'SkuId',
    `amount`      int                                                           NOT NULL COMMENT '购买数量',
    `money`       decimal(10, 2)                                                NOT NULL COMMENT '购买金额',
    `pay_time`    timestamp NULL DEFAULT NULL COMMENT '支付时间',
    `pay_status`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付状态',
    `del_flag`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY           `idx_user_id` (`user_id`),
    KEY           `idx_seller_id` (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';
-- 客诉订单表
CREATE TABLE `complainant_order`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'id',
    `complainant_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '客诉人id',
    `complainant_type`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '客诉人类型 buyer:买家;saller:卖家',
    `complainant_name`  varchar(32) COLLATE utf8mb4_general_ci                        NOT NULL COMMENT '客诉人名称',
    `order_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '订单id',
    `order_end_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '订单尾号',
    `order_create_time` datetime                                                      NOT NULL COMMENT '订单创建时间',
    `status`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '处理状态',
    `del_flag`          tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `update_time`       datetime                                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY                 `idx_create_time` (`create_time`),
    KEY                 `idx_order_end_id` (`order_end_id`),
    KEY                 `idx_complainant_name` (`complainant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客诉订单表';
/*
    背景: 存量订单10亿, 日订单增长百万量级。
    主查询场景如下:
    1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
    2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
    3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
    4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
    resources/sql中给出整体设计方案。包含存储基础设施选型, DDL（基础字段即可）, 满足上述场景设计思路。方案可描述大体方向，面试时可补充交流。
 */

/*
    存量订单10亿，日增长百万级，用100万进行带入。


    1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
    用户进行查询的时候，根据用户编号可以直接落定到确定数据表中，针对不到1000万行到数据，实时性能够保证。
    月数据量在3000起，以目前表结构并不复杂，2000万行数据性能还不算低下。
    数据库采用多(≥1)主多(≥1)从的形式部署，主库增删改，从库查询。
    但是针对日益增长的的数据，需要进行分库分表存储。订单号规则为 {订单号}{分隔符}{时间}{分隔符}{会员号后6位 不足补齐0}。
    针对存量10亿的数据，进行分库分表存储。为保证表的可用性，和可拓展性，原则上一张表在500-1000万就得进行分表（分库）存储。
    1,000,000,000 / 8库 / 32表 ≈ 平均390万行数据， 考虑到这种方式不可能绝对到平均，但是已经留出冗余的空间了。
    日增长 1,000,000 / 8库 / 32表 ≈ 平均日增长4000行数据。
    根据用户的会员号对8进行取余算库，对32进行取余算表。
    以这种形式进行分库，需要很久的时间才达到瓶颈。但是一般用户查询较早期的订单这种频率是很低的，以4000行数据的日增长，一年约为500万，这个数据量在性能上的影响非常小。
    所以这里设想以年为单位进行数据转移到冷备库，不影响高频访问的主要库（非主从意思）的效率，后面的查询可以在逻辑上进行相应的处理。

    2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
    增加一张卖家订单表，针对用户新增订单的事件（消息队列/Canal/flink等）进行监听，然后进行该数据存储。
    虽然说卖家订单和买家订单是一一对应的存在，但是针对查询效率要求没有那么高,可以根据实际硬件情况适当对分表的纬度进行缩小。
    也可同【1】点进行时间纬度的冷准库迁移。

    3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
    针对客诉数据，以订单日增长来100万来看，客诉订单理论上远远低于100万的。假设以20%来估计，月增长在600万。
    按时间客诉订单的创建时间的月份进行分表。半年钱的数据以冷备的形式移入冷备库。

    4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
    创建数据仓库，因数据仓库的实时性没有要求那么搞，可以采用异步同步的方式进行数据同步（消息队列/Canal/flink等）。
    有冷基础数据就可以根据具体的需求产生不同纬度的二次，n次统计的表。
    在处理的同时不会对现有的业务/服务产生影响。

    总结：
    以上都是针对最少添加外部依赖的形式想出来的解决方案，这样可以一定程度上降低成本（研发，维护等），毕竟引入一项技术就得解决引入这项技术带来的其他问题。
    如果有特殊需求，可以引入es搜索引擎，以及ShardingJDBC等框架或者中间件。

 */
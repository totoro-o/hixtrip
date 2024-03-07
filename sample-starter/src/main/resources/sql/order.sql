CREATE TABLE `t_order`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '订单号',
    `user_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '买家ID',
    `seller_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '卖家ID',
    `sku_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'skuId',
    `amount`      int                                                           NOT NULL COMMENT '购买数量',
    `money`       decimal(18, 2)                                                NOT NULL COMMENT '购买金额',
    `pay_time`    datetime                                                      DEFAULT NULL COMMENT '购买时间',
    `pay_status`  int                                                           NOT NULL COMMENT '支付状态（0-待支付，1-成功，2-失败）',
    `del_flag`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_by`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY           `IDX_SELLER_ID` (`seller_id`) USING BTREE COMMENT '卖家ID索引',
    KEY           `IDX_USER_ID` (`user_id`) USING BTREE COMMENT '买家ID索引',
    KEY           `IDX_CREATE_TIME` (`create_time`) USING BTREE COMMENT '创建时间索引',
    KEY           `IDX_ID_NAME_TIME` (`id`,`create_by`,`create_time`) USING BTREE COMMENT '客服查询索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';

-- 背景: 存量订单10亿, 日订单增长百万量级。
-- 主查询场景如下:
-- 1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
-- 2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
-- 3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
-- 4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
--
--
-- 1. 单表性能最好不要超过2000w,存量的10亿数据我们可以按只保留6个月的数据，其余的数据可以存入ES
-- 2. 基于日订单增长百万量级,需要进行分表分库,因为要满足买家的高并发、高时性查询所以分库维度以买家id进行水平分表将订单均摊到多个数据表中
-- 3. 为了满足卖家的高并发、高时性查询，我们需要建立一张索引表用来存储卖家订单与订单库表的映射关系，通过索引查询到对应的订单表
-- 4. 平台客服的需求可以与卖家一样生成对应的映射关系索引表，通过索引查询到对应的订单表
-- 5. 平台运营进行订单数据分析可以通过离线任务的形式进行统计分析

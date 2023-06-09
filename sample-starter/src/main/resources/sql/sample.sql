#
todo 你的建表语句,包含索引

/**
    以下表均添加 create_time,update_time,create_by update_by 字段
 */

/**
    假设一个 mysql 有10个库 每个库有 10 张表 那就是 10*10 张表 则取用户ID后2位 作为逻辑分库分表的依据
    用户端高频查询的是 通过 userId orderId
    管理端高频查询的是 通过 caseNo orderId

    userId 的生成策略可以是一个长的唯一数 如： 10032928123456789
    orderId 的生成策略可以是一个长的数字如 唯一数 + 用户ID后2位
    caseNo 的生成策略可以如 System.currentTimeMillis() + '4位随机数' + 用户ID后2位

    这样以最后3位为逻辑分库分表的依据，可以保证同一个用户的数据 订单数据在同一个库中

*/
CREATE TABLE `order`
(
    `order_id`            bigint                                 NOT NULL COMMENT '订单ID',
    `user_id`             bigint                                 NOT NULL COMMENT '用户ID',
    `order_address_id`    bigint                                 NOT NULL COMMENT '发货地址ID',
    `order_no`            varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
    `pay_id`              bigint                                 NOT NULL COMMENT '支付ID',
    `order_status`        int                                    NOT NULL COMMENT '订单状态',
    `total_amount`        bigint                                 NOT NULL COMMENT '订单金额（单位：分）',
    `pay_amount`          bigint                                 NOT NULL COMMENT '实际需支付金额 (单位：分)',
    `preferential_amount` bigint                                 NOT NULL COMMENT '优惠金额',
    `quantity`            bigint                                 NOT NULL COMMENT '商品数量',
    `finish_time`         datetime                                DEFAULT NULL COMMENT '订单完成时间',
    `user_remark`         varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户备注',
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `uni_order_no` (`order_no`) USING BTREE,
    KEY                   `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';


CREATE TABLE `order_item`
(
    `order_item_id`         bigint NOT NULL COMMENT '订单明细ID',
    `order_id`              bigint NOT NULL COMMENT '订单ID',
    `sku_id`                bigint NOT NULL COMMENT '商品SKU',
    `quantity`              bigint NOT NULL COMMENT '商品数量',
    `commodity_price`       bigint NOT NULL COMMENT '商品单价（单位:分）',
    `commodity_total_price` bigint NOT NULL COMMENT '商品总价（单位:分）',
    /* 省略 冗余的 商品信息（名称，主图 等） */
    PRIMARY KEY (`order_item_id`),
    KEY                     `idx_order_id` (`order_id`),
    KEY                     `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单明细';


CREATE TABLE `pay`
(
    `pay_id`        bigint                                  NOT NULL AUTO_INCREMENT COMMENT '支付ID',
    `pay_platform`  int                                     NOT NULL COMMENT '支付平台：如 1:支付宝 2微信',
    `pay_no`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付流水号',
    `amount`        bigint                                  NOT NULL COMMENT '金额（单位：分）',
    `pay_status`    int                                     NOT NULL COMMENT '支付状态 0-待支付 1-支付成功 2-支付失败',
    `pay_time`      datetime                                DEFAULT NULL COMMENT '支付时间',
    `pay_remark`    varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付备注',
    `notify_params` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回调参数',
    PRIMARY KEY (`pay_id`),
    UNIQUE KEY `uni_pay_no` (`pay_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='支付表';


CREATE TABLE `inventory`
(
    `inventory_id`         bigint                                 NOT NULL AUTO_INCREMENT COMMENT '库存ID',
    `sku_id`               varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'sku_id',
    `sellable_quantity`    bigint                                 NOT NULL COMMENT '可售库存',
    `withholding_quantity` bigint                                 NOT NULL COMMENT '预占库存',
    /* 还会有 仓库ID 啥的 */
    PRIMARY KEY (`inventory_id`),
    KEY                    `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存表';


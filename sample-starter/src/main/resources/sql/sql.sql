#todo
你的建表语句,包含索引

CREATE TABLE `order_info`
(
    `id`          VARCHAR(64)    NOT NULL COMMENT '订单号',
    `user_id`     VARCHAR(64)    NOT NULL COMMENT '购买人',
    `sku_id`      VARCHAR(64)    NOT NULL COMMENT 'SkuId',
    `amount`      INT            NOT NULL COMMENT '购买数量',
    `money`       DECIMAL(10, 2) NOT NULL COMMENT '购买金额',
    `pay_time`    DATETIME    DEFAULT NULL COMMENT '购买时间',
    `pay_status`  VARCHAR(32)    NOT NULL COMMENT '支付状态',
    `del_flag`    TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by`   VARCHAR(64) DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME       NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(64) DEFAULT NULL COMMENT '修改人',
    `update_time` DATETIME    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX         `idx_user_id` (`user_id`),
    INDEX         `idx_seller_id` (`seller_id`) ,
    INDEX         `idx_create_time` (`create_time`),
    INDEX         `idx_pay_info` (`pay_status`,`pay_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

--卖家、卖家需要频繁查找自己的订单，卖家ID、卖家ID单独加上索引
-- 订单支付状态通常伴随着支付时间建立组合索引

-- 分库分表：订单量级为2000万
-- 分库: 用户id --> 减少负载
-- 分表: 订单创建时间-->方便便管理历史数据和优化 eg 月度分割



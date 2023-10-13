#todo 你的建表语句,包含索引

CREATE TABLE `order` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家ID',
  `seller_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '卖家ID',
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'skuId',
  `amount` int NOT NULL COMMENT '购买数量',
  `money` decimal(18,2) NOT NULL COMMENT '购买金额',
  `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
  `pay_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付状态（0-待支付，1-成功，2-失败）',
  `del_flag` tinyint(1) NOT NULL COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `IDX_SELLER_ID` (`seller_id`) USING BTREE COMMENT '卖家ID索引',
  KEY `IDX_USER_ID` (`user_id`) USING BTREE COMMENT '买家ID索引',
  KEY `IDX_CREATE_TIME` (`create_time`) USING BTREE COMMENT '创建时间索引',
  KEY `IDX_PAYINFO` (`pay_status`,`pay_time`,`money`,`amount`) USING BTREE COMMENT '支付相关索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';


-- 因卖家、卖家需要频繁查找自己的订单，因此卖家ID、卖家ID需要各单独加上索引
-- 订单支付状态通常伴随着支付时间、购买金额、购买数量查询，以组合索引的方式建立

-- 订单量巨大的情况下采用水平分表：
-- 1、可以根据订单号hash取模的方式进行分表，但扩展的时候取模值发生改变，历史数据要做迁移。
-- 2、也可以订单时间去分表，例如2023年09月的订单数据存储在order_202309分表中，2023年10月的订单数据存储在order_202310分表中，但是会出现热点问题，用户可能查询最多的就是近个把月的数据、
-- 3、综上，取长补短，先根据时间范围进行第一次分表，再hash取模进行第二次分表。
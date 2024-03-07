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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';

-- 考虑到买家卖家的查询效率可以对买家id卖家id建立索引。
-- 数据不增长的情况下可以根据时间范围进行分片，让最新的数据尽量表的数据量少一点，商品大多数都是查询最近的单子
-- 可以对数据库做主从架构，读写分离提高数据的查询效率
#todo 你的建表语句,包含索引

CREATE TABLE `order` (
                         `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '订单号',
                         `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '购买人',
                         `sku_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT 'SkuId',
                         `amount` int NOT NULL DEFAULT '0' COMMENT '购买数量',
                         `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购买金额',
                         `pay_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '购买时间',
                         `pay_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '支付状态',
                         `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                         `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '创建人',
                         `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
                         `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
                         `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                         PRIMARY KEY (`id`),
                         KEY `idx_userId` (`user_id`),
                         KEY `idx_skuId` (`sku_id`),
                         KEY `idx_payStatus` (`pay_status`),
                         KEY `idx_createTime` (`create_time`),
                         KEY `idx_updateTime` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

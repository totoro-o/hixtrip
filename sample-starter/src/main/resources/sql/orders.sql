CREATE TABLE `orders` (
  `id` int(50) NOT NULL COMMENT '主键id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '购买人',
  `seller_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卖家',
  `sku_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SkuId',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购买金额',
  `pay_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '购买时间',
  `pay_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付状态',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `index_id_last_5` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '存储订单表id的后面5个字符，用作客服订单尾号查询',
  PRIMARY KEY (`id`),
  KEY `orders_index_user_id` (`user_id`) USING BTREE COMMENT '买家id索引，用在买家快速查看自己的订单',
  KEY `orders_index_seller_id` (`seller_id`) USING BTREE COMMENT '卖家id索引，用在卖家快速查看自己的订单',
  KEY `orders_index_create_time` (`create_time`) USING BTREE COMMENT '时间索引',
  KEY `orders_index_for_customer` (`user_id`,`index_id_last_5`,`create_time`) USING BTREE COMMENT '用户id、订单号后面5个字符串、时间的组合索引，供客服搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--如果是2000W数据不考虑增长，不需要分库分表
--10亿数据，日增百万，需要分库分表
--方案：
-- 1.针对买家、卖家订单分开存，也就是冗余订单表数据，实时性要求不一样
-- 2.针对买家的订单表，分库键用买家id，分表键用买家id加上日期，原则上同一个买家订单不允许落到不同的库，也就是不允许跨库，允许跨少量的分表
-- 3.针对卖家的订单表，分库键用卖家id，分表键用卖家id加上日期，原则上同一个卖家订单不允许落到不同的库，也就是不允许跨库，允许跨大量的分表，查询时间限定日期范围不超过三个月，保证跨的分表也不多
-- 4.平台客服频繁搜索客诉订单，先按照用户id确定订单在那个库，再按买家id、订单后后5位，时间作为条件查询，有对应的组合索引增加效率
-- 5. 平台运营进行订单数据分析，离线统计，按每个库统计再合并结果
-- 6.正常可查询的订单范围时间维度在1-3年即可，超过的数据可以同步到搜索引擎变成冷数据

--库存扣减只在缓存实现, 假设业务为秒杀场景，需要考虑高并发(100每秒)，避免超卖。要求无锁设计。
--方案：
-- 1.秒杀进入时使用redis的“DECRBY”命令将存储库存值减少，这是redis的原子操作，不需要锁。
-- 2.拿到这个命令返回值就是剩余库存量，通过判断剩余库存量大于0则秒杀成功;如果小于0则秒杀失败，之前“DECRBY”命令减掉的库存要加回来，避免超卖、少买
#todo 你的建表语句,包含索引

#2000W的数据分4张表,每张表500W,将卖家和买家的id%4来分配存入下标对应的表,主键id的起始分别为 1,2,3,4 然后以主键步长为4去自增就不会有重复
#设置了一个buyer_id_status,买家id与状态的组合索引, 买家查询时都会带买家id,能满足最左前缀原则.
#如果订单详情数据比较多可以考虑再垂直分一个订单详情表出去,列表展示的内容最好都能被索引覆盖
#只要sql尽量走索引,就能满足查询要求
CREATE TABLE `order_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `buyer_id` int DEFAULT NULL COMMENT '买家id',
  `seller_id` int DEFAULT NULL COMMENT '卖家id',
  `order_status` int DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`),
  KEY `buyer_id_status` (`buyer_id`,`order_status`) USING BTREE,
  KEY `order_id` (`order_id`),
  KEY `buyer_id` (`buyer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `buyer_id` int DEFAULT NULL COMMENT '买家id',
  `seller_id` int DEFAULT NULL COMMENT '卖家id',
  `order_status` int DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`),
  KEY `buyer_id_status` (`buyer_id`,`order_status`) USING BTREE,
  KEY `order_id` (`order_id`),
  KEY `buyer_id` (`buyer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_2` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `buyer_id` int DEFAULT NULL COMMENT '买家id',
  `seller_id` int DEFAULT NULL COMMENT '卖家id',
  `order_status` int DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`),
  KEY `buyer_id_status` (`buyer_id`,`order_status`) USING BTREE,
  KEY `order_id` (`order_id`),
  KEY `buyer_id` (`buyer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_3` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id',
  `buyer_id` int DEFAULT NULL COMMENT '买家id',
  `seller_id` int DEFAULT NULL COMMENT '卖家id',
  `order_status` int DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`),
  KEY `buyer_id_status` (`buyer_id`,`order_status`) USING BTREE,
  KEY `order_id` (`order_id`),
  KEY `buyer_id` (`buyer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sku` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '订单id',
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '库存id',
  `amount` int DEFAULT NULL COMMENT '库存数量',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设置关联 order表的索引';
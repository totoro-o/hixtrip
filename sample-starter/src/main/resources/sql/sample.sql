# todo 你的建表语句,包含索引

# todo 你的建表语句,包含索引
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `creatorId` bigint(20) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `modifierId` bigint(20) DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `number` varchar(64) DEFAULT NULL,
  `buyer_id` bigint(20) DEFAULT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `sku_qty` bigint(20) DEFAULT NULL,
  `sku_price` decimal(10,2) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `pay_status` int(1) DEFAULT NULL,
  `pay_number` varchar(256) DEFAULT NULL,
  `del` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_buyer_id` (`buyer_id`) USING HASH,
  KEY `idx_seller_id` (`seller_id`) USING HASH,
  KEY `idx_number` (`number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#分库设计：
    分库键：可以根据买家或卖家的ID进行分库。以便在高频订单查询时提高查询性能。
#分表设计：
    分表键：可以考虑使用订单的创建时间（例如按年份或月份）作为分表键。这样可以将订单按时间进行分散存储，以便在查询历史订单时提高性能。
    在每个分库中，可以创建多个分表来存储不同时间段的订单数据，以实现更好的查询性能和管理。



CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  `creatorId` bigint(20) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `modifierId` bigint(20) DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `sku_id` bigint(20) NOT NULL,
  `sellable_qty` bigint(20) DEFAULT NULL,
  `withholding_qty` bigint(20) DEFAULT NULL,
  `occupied_qty` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sku_id` (`sku_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




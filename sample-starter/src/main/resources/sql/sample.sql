DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
                             `id` int NOT NULL,
                             `sku_id` int DEFAULT NULL COMMENT '商品ID',
                             `sellable_quantity` bigint DEFAULT NULL COMMENT '可售数量',
                             `withholding_quantity` bigint DEFAULT NULL COMMENT '预占数量',
                             `occupied_quantity` bigint DEFAULT NULL,
                             `del_flag` int DEFAULT NULL COMMENT '删除标记',
                             `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`),
                             KEY `IDX_SKU_ID` (`sku_id`) USING BTREE COMMENT '商品ID',
                             KEY `IDX_CREATE_TIME` (`create_time`) USING BTREE COMMENT '创建时间索引',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `order` (
                         `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `user_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `seller_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `sku_id` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `amount` int DEFAULT NULL,
                         `money` decimal(8,2) DEFAULT NULL,
                         `pay_time` datetime DEFAULT NULL,
                         `pay_status` varchar(2) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `del_flag` bigint DEFAULT NULL,
                         `create_by` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `create_time` datetime DEFAULT NULL,
                         `update_by` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `IDX_SELLER_ID` (`seller_id`) USING BTREE COMMENT '卖家ID',
                         KEY `IDX_USER_ID` (`user_id`) USING BTREE COMMENT '买家ID',
                         KEY `IDX_CREATE_TIME` (`create_time`) USING BTREE COMMENT '创建时间索引',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


---- 考虑到买家卖家的查询效率，分别对卖家id和买家id建立索引
--- 分库分表可采用号段模式，比如滴滴的[tinyid](https://github.com/didi/tinyid) 结合shardingsphere进行分布式id的生成，通过时间分片，
---- 使其查询存量数据时，读请求相对均匀

---  在库存扣减的缓存方面

---- 如果是秒杀场景

----  1) 引入这个redis缓存后，也就是你默认这边只实现了一个增删改查功能的
---- 情况下,如果程序在执行查询的时候，虽然redis解决大量用户查数据库的压力
----     但是会出现一个问题,就是当你的库存(存量) 数量上亿的话，此时redis
----     存在大量的数据,其中数据中可能还存在大量的冷门数据,但是对用户来说
---    几乎很少查询

---     解法: 防止redis库存大量没必要的数据，未数据设置一个超时时间

---  这个是我的第一版方案

--    2) 根据上面的方案，又会又一个问题，因为你的大批量缓存
--    同一时间失效，可能导致大量请求，同时穿透缓存直达数据库，数据库会挂掉
--    所以设置以60s为起点的ttl时间 使其查询缓存的请求不在同一时间过期

--    3) 当黑客通过不正当手段访问链接，访问不存在的·数据，这个时候会发生缓存穿透，造成当机

--     解法: 穿透指的是查询一个根本不存在的数据，缓存和存储都不会命中
--    所以这个时候用空缓存 + 更新库存的缓存 构建两层保护 一个60s为起点生成不同
--     有效期的缓存，一个24h为起点，生成不同有效期的缓存

---    4) 冷门商品出现大量的访问，什么意思呢 就是说因为你的商品他的销售趋势不是一成不变的
---    可能你今天有一批热点商品，到明天就是冷门商品了，所以过往的冷门商品也可能出现大量访问

---    解法: 可能你更新商品到缓存里面的时候是一个数据，此时数据他被另一个线程更新了，
---    实际查询的数据与缓存数据不一致，所以这里用了redission的读写锁， 基于空缓存+本地缓存
---    的双缓存检测

---    5) 其中存在当数据量有几百万时，redis只能扛住10万的并发，可能导致redis挂掉，通过JVM扛并发的情况。

---    解法: 在到达缓存层之前 加JVM缓存扛住并发






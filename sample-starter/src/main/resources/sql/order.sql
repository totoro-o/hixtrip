/* #todo 你的建表语句,包含索引
# 背景: 存量订单10亿, 日订单增长百万量级。
#主查询场景如下:
1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
 */



--    ***********设计思路****************
-- 数据库层面采用分布式数据库解决方案,可以使用MySQL的分布式集群部署，以提供高可用
-- mysql数据库读写分离，冷热数据分离，缓解高并发
-- 进行分库分表, 根据订单创建时间进行分库，比如按年或按月；根据订单号进行分表
-- 建立索引优化查询
-- 考虑到订单数据的实时性和查询性能，可以考虑使用Redis等内存数据库作为缓存层，缓存热点订单数据，减少数据库压力。
-- 对于卖家和客服允许延迟的场景，可设计延迟数据库，可以用ES 查询，历史数据初始化时加载到ES中，实时数据使用阿里巴巴的Canal，解析MySQL的binlog来获取增量数据，同步数据到ES.

-- 创建第一个数据库 order_202403，表示2024年3月的订单数据
CREATE DATABASE order_202403;

-- 假设每个库分为100张表，表名格式为 order_yyyyMMdd_001 到 order_yyyyMMdd_100
-- 创建订单表 order_202403_066
CREATE TABLE `order_202403_066` (
    `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '订单号', -- 分表键
    `user_id` varchar(50) NOT NULL COMMENT '购买人',
    `seller_id` varchar(50) NOT NULL COMMENT '卖方',
    `sku_id` int(50) NOT NULL COMMENT 'skuId',
    `amount` int(11) DEFAULT NULL COMMENT '购买数量',
    `money` varchar(11) DEFAULT NULL COMMENT '购买金额',
    `pay_time` datetime DEFAULT NULL COMMENT '购买时间',
    `pay_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标志（0代表存在 1代表删除）',
    `order_tail_number` varchar(20) DEFAULT NULL COMMENT '订单尾号',
    `buyer_name` varchar(100) DEFAULT NULL COMMENT '买家姓名',
    `complaint_flag` tinyint(1) NOT NULL COMMENT '客诉标注（0代表未投死 1代表已投诉）',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间', -- 分库键
    `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`) USING BTREE, -- 用于时间范围查询，如订单数据分析
    KEY `idx_buyer` (`user_id`,`create_time`) USING BTREE,  -- 用于买家查询
    KEY `idx_seller` (`seller_id`,`create_time`) USING BTREE, -- 用于卖家查询
    KEY `idx_complaint` (`complaint_flag`,`create_time`) USING BTREE,-- 用于客服按客诉标志和时间范围查询
    KEY `idx_tail_name_time` (`order_tail_number`,`buyer_name`,`create_time`) USING BTREE -- 按订单尾号和买家姓名及时间范围查询
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';




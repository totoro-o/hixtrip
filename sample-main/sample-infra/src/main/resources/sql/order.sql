
/*----------------------------------------------------------------
背景: 存量订单10亿, 日订单增长百万量级。
主查询场景如下:
1. 买家频繁查询我的订单, 高峰期并发100左右。实时性要求高。
2. 卖家频繁查询我的订单, 高峰期并发30左右。允许秒级延迟。
3. 平台客服频繁搜索客诉订单(半年之内订单, 订单尾号，买家姓名搜索)，高峰期并发10左右。允许分钟级延迟。
4. 平台运营进行订单数据分析，如买家订单排行榜, 卖家订单排行榜。
----------------------------------------------------------------*/

/*----------------------------------------------------------------
 考虑到存量数据已经超过亿级，需要对冷热数据分库分表操作，设计两类表一个是冷数据订单表、一个是热数据订单表，
 以一个月为界线，订单创建时间超过一个月就将改数据移入冷库中，并且按照id取余+月份定位对应的表
 热数据订单存储为近一个月的数据，预估数量保持在千万级别，同样需要分表操作，按照id取余+月份定位对应的表
----------------------------------------------------------------*/

-- 业务区分冷热双库
CREATE TABLE `order_{cold_or_warm}_{db_index}_{month_index}` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单号',
  `user_id` VARCHAR(64) NOT NULL COMMENT '购买人',
  `seller_id` VARCHAR(64) NOT NULL COMMENT '卖家人',
  `sku_id` VARCHAR(64) NOT NULL COMMENT 'SkuId',
  `amount` INT NOT NULL COMMENT '购买数量',
  `money` DECIMAL(10, 2) NOT NULL COMMENT '购买金额',
  `pay_time` DATETIME DEFAULT NULL COMMENT '购买时间',
  `pay_status` VARCHAR(32) NOT NULL COMMENT '支付状态',
  `del_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_seller_id` (`seller_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

--由于并发不高且允许分钟级延迟，可以考虑Elasticsearch等搜索引擎进行全文检索和排序
CREATE TABLE complaint_orders (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '客诉订单ID',
    `order_end_id` VARCHAR(32) COMMENT '客诉订单ID尾号',
    `complainant` VARCHAR(32) COMMENT '投诉人（可能是买家或卖家）',
    `complainant_name` VARCHAR(32) COMMENT '投诉人名称',
    `order_create_time` DATETIME NOT NULL COMMENT '订单创建时间',
    `del_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by` VARCHAR(64) DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT NULL COMMENT '修改人',
    `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_order_create_time` (`order_create_time`),
    INDEX `idx_order_id_name` (`seller_id`,`complainant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客诉订单';



--平台运营进行订单数据分析，通常这类查询是批量且低频的，涉及到大数据分析。可以定期将订单数据同步到数据仓库，
--如Hadoop或ClickHouse，进行离线分析和报表生成

--整体存储基础设施选型
-- 关系型数据库：MySQL/PostgreSQL用于存储订单的核心业务数据，提供ACID事务保障。
-- 分布式缓存：Redis/Memcached用于缓存高频查询的买家和卖家订单信息。
-- 搜索引擎：Elasticsearch用于快速搜索和筛选客诉订单。
-- 数据仓库：Hadoop/ClickHouse用于订单数据分析和报表生成。
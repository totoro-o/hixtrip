-- 假设我们分为4个数据库，每个数据库存储一部分用户的订单
-- 数据库名示例：order_db_0, order_db_1, order_db_2, order_db_3
-- 每个数据库中的表名示例：order_202401, order_202402, ..., order_202412
-- 注意：{db_suffix} 和 {month_suffix} 是占位符，实际应用时需要替换为具体的数据库后缀和月份后缀。
-- 例如：order_0_202301 表示第0个数据库中2023年1月份的订单表。

-- 创建订单表的SQL模板，需要为每个数据库和每个月份创建相应的表
CREATE TABLE `order_{db_suffix}_{month_suffix}` (
  `id` VARCHAR(64) NOT NULL COMMENT '订单号',
  `user_id` VARCHAR(64) NOT NULL COMMENT '购买人',
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
  INDEX `idx_create_time` (`create_time`),
  INDEX `idx_pay_status` (`pay_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 分库分表策略：考虑到订单量级为2000万，我们可以选择按照用户ID进行分库，以平衡买家查询的负载。同时，我们可以按照时间范围（例如每月）进行分表，以便于管理历史数据和优化查询性能。
-- 分库键：userId 字段，因为买家会频繁查询自己的订单，使用 userId 作为分库键可以将一个买家的所有订单放在同一个数据库中，从而提高查询效率
-- 分表键：createTime 字段，按月分表可以有效地管理数据的增长，并且可以根据订单创建时间快速定位到具体的分表。
-- 索引设计：为了优化查询性能，我们可以为 userId、createTime 和 payStatus 字段创建索引。userId 和 createTime 是常用的查询条件，而 payStatus 可能用于筛选特定状态的订单。
-- 分库分表的具体实现可以使用MyCAT中间件来完成。

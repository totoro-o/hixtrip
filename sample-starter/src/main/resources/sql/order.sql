
CREATE TABLE `order` (
                         `id` BIGINT(20) NOT NULL COMMENT '主键id',
                         `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
                         `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '购买人',
                         `seller_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '卖家id',
                         `sku_id` VARCHAR(50) NULL DEFAULT NULL COMMENT 'SkuId',
                         `amount` INT(11) NULL DEFAULT NULL COMMENT '购买数量',
                         `money` DECIMAL(12,4) NULL DEFAULT NULL COMMENT '购买金额',
                         `pay_time` DATETIME NULL DEFAULT NULL COMMENT '购买时间',
                         `pay_status` VARCHAR(10) NULL DEFAULT NULL COMMENT '支付状态(wait支付中;paying支付中;success支付成功;failed支付失败)',
                         `del_flag` VARCHAR(1) NULL DEFAULT NULL COMMENT '删除标志（0代表存在 1代表删除）',
                         `createBy` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人',
                         `createTime` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
                         `updateBy` VARCHAR(50) NULL DEFAULT NULL COMMENT '修改人',
                         `updateTime` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
                         PRIMARY KEY (`id`),
                         INDEX `user_id` (`user_id`),
                         INDEX `seller_id` (`seller_id`),
                         INDEX `del_flag` (`del_flag`)
)
    COMMENT='订单表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `order_detail` (
                                `id` BIGINT(20) NOT NULL COMMENT '主键id',
                                `order_id` VARCHAR(50) NOT NULL COMMENT '订单id',
                                `sku_id` VARCHAR(50) NOT NULL COMMENT 'SKUID',
                                `price` DECIMAL(12,4) NOT NULL COMMENT '价格',
                                `quantity` INT(11) NOT NULL COMMENT '数量',
                                `del_flag` VARCHAR(1) NULL DEFAULT NULL COMMENT '删除标志（0代表存在 1代表删除）',
                                `createBy` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人',
                                `createTime` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
                                `updateBy` VARCHAR(50) NULL DEFAULT NULL COMMENT '修改人',
                                `updateTime` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`),
                                INDEX `order_id` (`order_id`),
                                INDEX `del_flag` (`del_flag`)
)
    COMMENT='订单明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

--------------------------设计思路补充--------------------------
买家查询：可以将买家的订单信息缓存在Redis中，通过订单 ID 或买家 ID 进行快速查询。同时，定期将缓存中的数据与数据库进行同步，以保证数据的一致性。
卖家查询：实时性要求相对较低，可以直接查询数据库。为了提高查询效率，可以根据卖家ID对订单进行索引。
客服搜索：可以使用搜索引擎（如 Elasticsearch）来实现快速搜索。将订单数据导入到 Elasticsearch 中，并建立相关的索引，以便根据订单尾号和买家姓名进行搜索。
订单数据分析：可以使用数据仓库（如 Hive 或 ClickHouse）来存储和分析订单数据。通过定期将订单数据导入到数据仓库中，可以进行各种分析和报表生成。
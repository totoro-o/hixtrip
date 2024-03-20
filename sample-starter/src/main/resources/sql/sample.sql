#todo 你的建表语句,包含索引



CREATE TABLE `orders` (
                          `id` VARCHAR ( 32 ) NOT NULL COMMENT '订单号',
                          `user_id` VARCHAR ( 32 ) NOT NULL COMMENT '购买人',
                          `seller_id` VARCHAR ( 32 ) NOT NULL COMMENT '卖家id',
                          `sku_id` VARCHAR ( 32 ) NOT NULL COMMENT 'SkuId',
                          `amount` INT NOT NULL COMMENT '购买数量',
                          `money` DECIMAL ( 10, 2 ) NOT NULL COMMENT '购买金额',
                          `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '支付时间',
                          `pay_status` VARCHAR ( 255 ) NOT NULL COMMENT '支付状态',
                          `del_flag` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                          `create_by` VARCHAR ( 32 ) DEFAULT NULL COMMENT '创建人',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_by` VARCHAR ( 32 ) DEFAULT NULL COMMENT '修改人',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                          PRIMARY KEY ( `id` ),
                          KEY `idx_user_id` ( `user_id` ),
                          KEY `idx_seller_id` ( `seller_id` ),
                          KEY `idx_create_time` (`create_time`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表';

/*

    1、选择user_id与seller_id作为索引是因为买家卖家通过【我的订单】查询订单，通常都是由user_id与seller_id查询订单，这个索引可以加快根据买家ID进行的查询操作。
    2、选择create_time作为索引是因为我的订单多是按照create_time字段排序，查询时数据库引擎可以利用 idx_create_time索引来直接获取有序的订单数据，而不需要进行额外的排序操作。这样可以大大减少查询所需的时间复杂度，提高查询性能
    3、使用哈希用户id作为分库键，通过哈希分库的方式可以根据用户ID将订单分配到不同的数据库中，既能够保证数据的分布均匀性，又能够降低管理和维护的成本。
    4、使用create_time作为分表键，考虑【我的订单】通常为日期较为靠近的是更为热点的数据，根据时间范围分表可以有效减少查询数据的数量，提高查询速度。
        如果效率不高可以再使用create_time与user_id或seller_id作为复合分表键提高查询效率

 */
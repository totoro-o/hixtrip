-- noinspection SqlNoDataSourceInspectionForFile
-- 用户订单分表数据库
CREATE TABLE `orders_user_${index}` (
                          `id` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_0900_ai_ci',
                          `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '买家唯一标识' COLLATE 'utf8mb4_0900_ai_ci',
                          `business_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '卖家唯一标识' COLLATE 'utf8mb4_0900_ai_ci',
                          `sku_id` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                          `amount` INT(10) NULL DEFAULT NULL,
                          `money` DECIMAL(20,6) NULL DEFAULT NULL,
                          `pay_time` TIMESTAMP NULL DEFAULT NULL,
                          `pay_status` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                          `pay_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '支付订单号' COLLATE 'utf8mb4_0900_ai_ci',
                          `order_status` VARCHAR(50) NULL DEFAULT NULL COMMENT '订单状态' COLLATE 'utf8mb4_0900_ai_ci',
                          `del_flag` BIGINT(19) NULL DEFAULT NULL,
                          `create_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                          `create_time` TIMESTAMP NULL DEFAULT NULL,
                          `update_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                          `update_time` TIMESTAMP NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `user_id` (`user_id`) USING BTREE
) COMMENT='订单表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB;
-- 商家订单表分库
CREATE TABLE `orders_shop_${index}` (
                                        `id` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_0900_ai_ci',
                                        `user_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '买家唯一标识' COLLATE 'utf8mb4_0900_ai_ci',
                                        `business_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '卖家唯一标识' COLLATE 'utf8mb4_0900_ai_ci',
                                        `sku_id` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                                        `amount` INT(10) NULL DEFAULT NULL,
                                        `money` DECIMAL(20,6) NULL DEFAULT NULL,
                                        `pay_time` TIMESTAMP NULL DEFAULT NULL,
                                        `pay_status` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                                        `pay_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '支付订单号' COLLATE 'utf8mb4_0900_ai_ci',
                                        `order_status` VARCHAR(50) NULL DEFAULT NULL COMMENT '订单状态' COLLATE 'utf8mb4_0900_ai_ci',
                                        `del_flag` BIGINT(19) NULL DEFAULT NULL,
                                        `create_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                                        `create_time` TIMESTAMP NULL DEFAULT NULL,
                                        `update_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
                                        `update_time` TIMESTAMP NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `business_id` (`business_id`) USING BTREE
) COMMENT='订单表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB;

-- 总体思路 基于用户分表 + 冗余法
-- 1、根据user_id进行分表，按照用户最后一位来进行分表，数据添加到orders_user_${index}表中
-- 2、通过异步的方式，根据商家business_id进行不同的分表，将订单同步到orders_business_${index}表中
-- 3、在orders_user_${index}添加根据用户user_id查询的索引，orders_business_{index} 中添加根据business_id查询的索引
-- 4、数据量在2000w左右，不需要分太多的表，可以先分5个表，mysql的数据库对于2000w的数据其实可以不用分表，如果未来一年之内，数据会大量增长，可以考虑分5个表，每个表数据量控制在1亿以下
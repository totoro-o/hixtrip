# todo 你的建表语句,包含索引

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for maill-order-item
-- ----------------------------
DROP TABLE IF EXISTS `mail-order-item`;
CREATE TABLE `mail-order-item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `sku_id` bigint(64) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `status` tinyint(2) NOT NULL COMMENT '订单状态,1:等待支付，2：支付成功 , -1:支付失败',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `user_id` bigint(15) NOT NULL COMMENT '用户id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `sku_id`(`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall-order
-- ----------------------------
DROP TABLE IF EXISTS `mall-order`;
CREATE TABLE `mall-order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `status` tinyint(2) NOT NULL COMMENT '订单状态,1:等待支付，2：支付成功 , -1:支付失败',
   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `quantity` int(11) NOT NULL COMMENT '订单数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '订单总价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall-sku
-- ----------------------------
DROP TABLE IF EXISTS `mall-sku`;
CREATE TABLE `mall-sku`  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall-stock
-- ----------------------------
DROP TABLE IF EXISTS `mall-stock`;
CREATE TABLE `mall-stock`  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `sku_id` bigint(64) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '库存数量',
   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id`(`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

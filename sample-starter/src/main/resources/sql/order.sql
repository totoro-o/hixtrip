/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 14/03/2024 10:09:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
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

SET FOREIGN_KEY_CHECKS = 1;

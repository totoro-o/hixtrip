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

 Date: 14/03/2024 13:49:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
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

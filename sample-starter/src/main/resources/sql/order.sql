

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_o
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `total_num` int(11) DEFAULT NULL COMMENT '数量合计',
  `moneys` int(11) DEFAULT NULL COMMENT '金额合计',
  `pay_type` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型，1、在线支付、0 货到付款',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
  `create_by` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  `recipients` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `recipients_mobile` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机',
  `recipients_address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人地址',
  `weixin_transaction_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `order_status` int(1) DEFAULT NULL COMMENT '订单状态,0:未完成,1:已完成，2：已退货',
  `pay_status` int(1) DEFAULT NULL COMMENT '支付状态,0:未支付，1：已支付，2：支付失败',
  `del_flag` int(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `status` (`order_status`),
  KEY `payment_type` (`pay_type`),
  KEY `sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='订单明細表';

SET FOREIGN_KEY_CHECKS = 1;

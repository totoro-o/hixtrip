#todo 你的建表语句,包含索引

CREATE TABLE `order`  (
        `id` varchar(64) NOT NULL COMMENT '主键ID',
        `sku_id` varchar(64) NOT NULL COMMENT 'skuid',
        `amount` int(0) NOT NULL COMMENT '购买数量',
        `money` decimal(10, 2) NOT NULL COMMENT '购买金额',
        `pay_time` datetime(0) NOT NULL COMMENT '购买时间',
        `user_id` varchar(64) NOT NULL COMMENT '买家ID',
        `seller_id` varchar(64) NOT NULL COMMENT '卖家ID',
        `pay_status` char(1) NOT NULL COMMENT '支付状态',
        `del_flag` tinyint(1) NOT NULL COMMENT '删除标志',
        `create_by` varchar(64) NOT NULL COMMENT '创建人',
        `create_time` datetime(0) NOT NULL COMMENT '创建时间',
        `update_by` varchar(64) NULL COMMENT '修改人',
        `update_time` datetime(0) NULL COMMENT '修改时间',
        PRIMARY KEY (`id`),
        INDEX `key_user_id`(`user_id`) USING BTREE COMMENT '买家索引',
        INDEX `key_seller_id`(`seller_id`) USING BTREE COMMENT '卖家索引'
) COMMENT = '订单表';


# 因为需要卖家或买家查询订单,所以买家和卖家字段添加索引。
# 分表可以根据创建人ID进行分别，分表策略可以采用create_by字段hashcode值对表的数量取模作为表后缀，可以根据时间对表进行分区提升查询效率。
# 可以增加订单log日志表，log日志表可以根据卖家ID进行hashcode值取模，方便卖家查询订单数据，买家和卖家通过不同的表去查询数据，达到同一张表查询时不同的分表字段
# 分库策略可以根据业务量，按照年+月的形式作为库的后缀，或者根据年份分库
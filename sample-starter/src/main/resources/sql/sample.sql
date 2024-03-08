#todo 你的建表语句,包含索引
CREATE TABLE hixtrip.`order` (
                                 id varchar(255) NOT NULL COMMENT '订单号',
                                 user_id VARCHAR(100) NOT NULL COMMENT '购买人',
                                 sku_id varchar(100) NOT NULL COMMENT 'SkuId',
                                 amount INT NOT NULL COMMENT '购买数量',
                                 money DECIMAL NOT NULL COMMENT '购买金额',
                                 pay_time DATETIME NOT NULL COMMENT '购买时间',
                                 pay_status varchar(100) NOT NULL COMMENT '支付状态',
                                 del_flag TINYINT DEFAULT 0 NOT NULL COMMENT '删除标志（0代表存在 1代表删除）',
                                 create_by varchar(100) NOT NULL COMMENT '创建人',
                                 create_time DATETIME NOT NULL COMMENT '创建时间',
                                 update_by varchar(100) NULL COMMENT '修改人',
                                 update_time DATETIME NULL COMMENT '修改时间',
                                 CONSTRAINT order_PK PRIMARY KEY (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci
COMMENT='订单表';

CREATE INDEX order_user_id_IDX USING BTREE ON hixtrip.`order` (user_id);
CREATE INDEX order_pay_time_IDX USING BTREE ON hixtrip.`order` (pay_time,pay_status);


/**
  2000w的数据可以水平分order表，分成4份，以购买人的id % 4取余判断订单数据放在哪张表
 */
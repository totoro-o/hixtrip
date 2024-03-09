-- 订单表DDL
CREATE TABLE t_order (
    id           VARCHAR(64)     NOT NULL            COMMENT '订单号',
    user_id      VARCHAR(64)     NOT NULL            COMMENT '买家ID',
    seller_id    VARCHAR(64)     NOT NULL            COMMENT '卖家ID',
    sku_id       VARCHAR(64)     NOT NULL            COMMENT 'skuId',
    amount       INT             NOT NULL            COMMENT '购买数量',
    money        DECIMAL(18,2)   NOT NULL            COMMENT '购买金额',
    pay_time     DATETIME        DEFAULT NULL        COMMENT '购买时间',
    pay_status   VARCHAR(1)      NOT NULL            COMMENT '支付状态(-2 超时未支付, -1 支付失败, 0 未支付, 1 支付成功)',
    del_flag     TINYINT         DEFAULT 0 NOT NULL  COMMENT '删除标志(0 代表存在 1 代表删除)',
    create_by    VARCHAR(100)    NOT NULL            COMMENT '创建人',
    create_time  DATETIME        NOT NULL            COMMENT '创建时间',
    update_by    VARCHAR(100)    DEFAULT NULL        COMMENT '修改人',
    update_time  DATETIME        DEFAULT NULL        COMMENT '修改时间'
    PRIMARY KEY (id),
    KEY idx_user_id (user_id, create_time),
    KEY idx_seller_id (seller_id, create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 索引
-- 由于主要查询场景为买家、卖家查询我的订单，因此建立买家ID与订单创建时间的联合索引 和 卖家ID与订单创建时间的联合索引，
-- 这样即可通过买家ID与卖家ID和订单时间等条件高效查询指定数据。

-- 分表方案
-- 买家ID分表 OR 卖家ID分表？
-- 由于使用卖家ID分表容易产生数据倾斜等问题，所以这里采用买家ID分表。
-- 且通过买家ID分表，可满足买家查询订单实时性要求高的需求。
-- 卖家查询订单，秒级延迟如何保证？
-- 可以考虑实时同步一张卖家维度的分表，然后再根据卖家ID查询即可满足卖家查询秒级延迟的需求，
-- 缺点是浪费空间，本质上是空间换时间的方案。


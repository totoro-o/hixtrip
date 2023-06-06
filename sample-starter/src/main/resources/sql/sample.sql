#
todo 你的建表语句,包含索引

    -- 订单表
CREATE TABLE order_info
(
    order_id     INT PRIMARY KEY,
    order_number VARCHAR(50)    NOT NULL,
    buyer_id     INT            NOT NULL,
    seller_id    INT            NOT NULL,
    order_date   DATETIME       NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status VARCHAR(20)    NOT NULL,
    -- 其他需要的字段...
)

-- 创建分库分表索引
    PARTITION BY HASH(buyer_id) PARTITIONS 8;
CREATE INDEX idx_buyer_id ON order_info (buyer_id);
-- 使用buyer_id进行哈希分区，将数据分散存储到8个分库中。这样可以将订单数据均匀地分布在不同的库中，提高查询和写入的并发性能。
-- 创建idx_buyer_id索引可以快速定位到特定buyer_id的订单数据。

-- 库存表
CREATE TABLE inventory
(
    sku_id         INT PRIMARY KEY,
    sellableQuantity BIGINT ,
    -- 其他库存字段...
)
-- 创建索引
CREATE INDEX idx_sku_id ON inventory (sku_id);

-- 创建索引为查询频率较高的字段，分库分表可以根据卖家和买家高频订单查询的需求进行划分，
-- 可以将订单按照买家ID进行分库，买家id进行分表
-- 如果数据量在500w左右可以不考虑分表但是为了之后扩展可以考虑分表，比如订单可以按照时间进行分表，根据实际业务需求选分表键
-- 可以使用归档表对历史订单的删除
-- 索引设计使用高频字段比如卖家id 买家id 作为索引

#todo 你的建表语句,包含索引

1、订单表 - 含有订单id、买家id（我的订单）、卖家id（我的订单）等
CREATE TABLE Order (
    order_id BIGINT PRIMARY KEY,
    buyer_id BIGINT,
    seller_id BIGINT,
    order_amount DECIMAL(10, 2),
    order_time TIMESTAMP,
    ...
    KEY 'idx_buyer_id'(buyer_id) USING BTREE,
    KEY 'idx_seller_id'(seller_id) USING BTREE

);

2、订单详情表 - 包含订单id、商品id等
CREATE TABLE OrderDetail (
    order_detail_id BIGINT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price DECIMAL(8, 2),
    ...
    KEY 'idx_product_id'(product_id) USING BTREE
);

3、客诉订单表 - 包含订单id（订单号查询）、买家姓名、数据类型（冷热）、创建时间、修改时间等
CREATE TABLE ComplaintOrder (
    complaint_order_id BIGINT PRIMARY KEY,
    order_id BIGINT,
    buyer_name VARCHAR(255),
    data_type INT,
    create_time TIMESTAMP,
    update_time TIMESTAMP,
    ...
    KEY 'idx_buyer_name'(buyer_name) USING BTREE
);

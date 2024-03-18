#todo 你的建表语句,包含索引

CREATE TABLE `ORDER_DATA` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(255),
    `sku_id` VARCHAR(255),
    `amount` INT,
    `money` DECIMAL(19, 2),
    `pay_time` DATETIME,
    `pay_status` VARCHAR(255),
    `del_flag` BIGINT,
    `create_by` VARCHAR(255),
    `create_time` DATETIME,
    `update_by` VARCHAR(255),
    `update_time` DATETIME,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- 分库策略：通过分库分表消息中间件 sharding-jdbc 实现，将id作为分库分表键、分表键、然后生成雪花id %4 去分配数据
-- 数据库分为两个节点 interview_0、interview_1 来减轻单个数据库并发压力
-- 将 ORDER_DATA 分成 ORDER_DATA_0、ORDER_DATA_1、ORDER_DATA_2、ORDER_DATA_3 （每500w条数据一张表）
-- 在 user_id 添加普通索引，提升买、卖家对订单查询的效率
-- sharding-jdbc 的 yml 配置 如下，需要移除原有的数据源配置
--spring:
--  shardingsphere:
--    datasource:
--      #定义了数据源的名称
--      names: ds0,ds1
--      ds0:
--        type: com.zaxxer.hikari.HikariDataSource
--        driverClassName: com.mysql.cj.jdbc.Driver
--        jdbcUrl: jdbc:mysql://localhost:3306/interview_0
--        username: root
--        password: 123456
--      ds1:
--        type: com.zaxxer.hikari.HikariDataSource
--        driverClassName: com.mysql.cj.jdbc.Driver
--        jdbcUrl: jdbc:mysql://localhost:3306/interview_1
--        username: root
--        password: 123456
--    sharding:
--      tables:
--        ORDER_DATA:
--          #定义了实际数据节点，用于表示数据分片的数据源范围和表范围
--          actualDataNodes: ds0.ORDER_DATA_$->{0..1},ds1.ORDER_DATA_$->{2..3}
--          #定义了分表策略，这里使用了 inline 策略，根据id列进行分片
--          tableStrategy:
--            inline:
--              shardingColumn: id
--              algorithmExpression: ORDER_DATA_$->{id%4}
--          #主键策略
--          keyGenerator:
--            column: id
--            #主键通过雪花算法生成
--            type: SNOWFLAKE
--    #打开日志记录
--    props:
--      sql:
--        show: true


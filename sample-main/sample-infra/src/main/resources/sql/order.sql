--创建订单表
CREATE TABLE `order` (
`id` VARCHAR(30), -- 订单号，自增
`buyer_name` VARCHAR(50) , -- 购买人姓名
`product_code` VARCHAR(50) , -- 商品编码
`quantity` INT(11) , -- 购买数量
`amount` DECIMAL(10,2) , -- 购买金额
`purchase_time` TIMESTAMP, -- 购买时间
`payment_status` VARCHAR(50) DEFAULT NULL, -- 支付状态
`delete_flag` TINYINT(1) DEFAULT '0', -- 删除标志，0代表存在，1代表删除
`creator_name` VARCHAR(50) , -- 创建人姓名
`create_time` TIMESTAMP, -- 创建时间
`modifier_name` VARCHAR(50), -- 修改人姓名
`modify_time` TIMESTAMP, -- 修改时间
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; -- 使用InnoDB引擎，字符集为utf8

--根据购买时间月份进行创建分区
PARTITION BY RANGE (UNIX_TIMESTAMP(purchase_time)) (
  PARTITION p0 VALUES LESS THAN (UNIX_TIMESTAMP('2023-08')),
  PARTITION p1 VALUES LESS THAN (UNIX_TIMESTAMP('2023-09')),
  PARTITION p2 VALUES LESS THAN (UNIX_TIMESTAMP('2023-10'))
);

--创建分区存储过程
-- 使用CURDATE()函数获取当前日期，并通过DATE_FORMAT函数将其格式化为%Y%m的形式。
-- 然后，使用QUOTE函数对下一个日期进行引用。
-- 构造出一个ALTER TABLE语句来创建新的分区，
-- 并使用PREPARE和EXECUTE语句来执行动态SQL。
DELIMITER //
CREATE PROCEDURE create_partition()
BEGIN
  DECLARE current_date DATE;
  SET current_date = CURDATE();

  SET @partition_name = CONCAT('p', DATE_FORMAT(current_date, '%Y%m'));
  SET @sql = CONCAT('ALTER TABLE `order` ADD PARTITION (PARTITION ', @partition_name, ' VALUES LESS THAN (TO_DAYS(', QUOTE(DATE_ADD(current_date, INTERVAL 1 month)), ')))');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT 'Partition created: ', @partition_name;
END
//DELIMITER;
--事件调度 每月一次实现自动创建下个月的表分区
--我们使用EVERY 1 DAY表示每天执行一次，使用STARTS CURRENT_TIMESTAMP + INTERVAL 1 DAY表示从明天开始执行。调度器将会在每天的特定时间执行存储过程。
CREATE EVENT create_partition_event
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP + INTERVAL 1 month
DO
BEGIN
    -- 执行的创建分区存储过程
    CALL create_partition();
END

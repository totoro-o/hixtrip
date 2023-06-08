package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


/**
 * 库存DO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_number")
    private String orderNumber;

    @TableField(value = "order_status")
    private String orderStatus;

    @TableField(value = "total_amount")
    private BigDecimal totalAmount;
   //其他属性
}

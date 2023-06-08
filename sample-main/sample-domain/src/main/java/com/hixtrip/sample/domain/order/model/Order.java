package com.hixtrip.sample.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    //自增值
    private Long id;
    private String skuId;
    private Integer skuNum;
    private BigDecimal orderPrice;
    private String consignee;
    private String consigneeTel;
    private String orderNumber;
    private String userId;
    private String paymentWay;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String processStatus;
    private Date createTime;
    private Date expireTime;
}

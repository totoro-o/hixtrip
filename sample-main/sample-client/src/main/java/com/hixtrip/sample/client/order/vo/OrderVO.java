package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单返回值
 */
@Data
@Builder
public class OrderVO {
    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;

    /**
     * 卖家id
     */
    private String sellerId;

    /**
     * SkuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 订单创建状态
     */
    private String createCode;

    /**
     * 订单创建信息
     */
    private String createMsg;
}

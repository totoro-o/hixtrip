package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("order")
public class OrderDO extends BaseDO {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 下单用户id
     */
    private Long userId;

    /**
     * 下单的skuId
     */
    private String skuId;

    /**
     * 下单的SKU数量
     */
    private Long num;

    /**
     * 下单的SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付状态
     */
    private String payStatus;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付链接
     */
    private String payUrl;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 第三方订单号 如微信支付的transaction_id
     */
    private String thirdPartySerialNumber;
}

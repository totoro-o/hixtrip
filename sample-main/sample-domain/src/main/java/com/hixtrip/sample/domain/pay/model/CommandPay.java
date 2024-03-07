package com.hixtrip.sample.domain.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * sku
     */
    private String skuId;
    /**
     * 购买数量
     */
    private Long amount;

    /**
     * 订单状态
     */
    private String payStatus;
}
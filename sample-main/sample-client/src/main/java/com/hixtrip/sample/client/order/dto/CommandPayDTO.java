package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付状态 success 成功 ,repeat 重复 ,fail 失败
     */
    private String payStatus;

    /**
     * 支付类别。如：alipay、wxPay等
     */
    private String payType;


}

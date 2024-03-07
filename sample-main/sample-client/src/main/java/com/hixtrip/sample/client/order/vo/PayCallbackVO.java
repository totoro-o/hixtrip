package com.hixtrip.sample.client.order.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayCallbackVO {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 支付类别。如：alipay、wxPay等
     */
    private String payType;
}

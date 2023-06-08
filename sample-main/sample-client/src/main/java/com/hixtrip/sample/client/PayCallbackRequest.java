package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayCallbackRequest {
    private String orderId; // 订单ID
    private String paymentId; // 支付ID
    private String paymentStatus; // 支付状态
    private String callbackType;//返回类型
    //....

}

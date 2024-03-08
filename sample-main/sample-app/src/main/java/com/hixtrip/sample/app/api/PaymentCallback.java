package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付回调策略接口
 * @创建人 liyinglong
 * @创建时间 2024/3/7
 * @描述
 */
public interface PaymentCallback {
    void handlePaymentResult(Order order, String payStatus);
}

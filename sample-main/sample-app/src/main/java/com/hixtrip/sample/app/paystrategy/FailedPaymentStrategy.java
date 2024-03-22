package com.hixtrip.sample.app.paystrategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;

public class FailedPaymentStrategy implements PaymentStrategy {
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public void handlePaymentResult(String orderId) {
        // 处理支付失败的逻辑
        orderDomainService.orderPayFail(orderId);
    }
}

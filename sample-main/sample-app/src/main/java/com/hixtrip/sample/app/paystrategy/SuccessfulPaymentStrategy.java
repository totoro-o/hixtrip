package com.hixtrip.sample.app.paystrategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;

public class SuccessfulPaymentStrategy implements PaymentStrategy {
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public void handlePaymentResult(String orderId) {
        // 处理支付成功的逻辑
        orderDomainService.orderPaySuccess(orderId);
    }
}


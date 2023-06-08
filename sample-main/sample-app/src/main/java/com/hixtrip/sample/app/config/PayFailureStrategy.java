package com.hixtrip.sample.app.config;

import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFailureStrategy implements PayCallbackStrategy {
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String handlePayCallback(PayCallbackRequest request) {
        // 处理支付失败的逻辑，例如记录日志、发送通知等
        orderDomainService.orderPayFail(request.getOrderId());
        return PayCallbackType.PAY_FAILURE.getDescription();
    }

}

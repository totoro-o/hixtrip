package com.hixtrip.sample.app.config;

import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatPayCallbackStrategy implements PayCallbackStrategy{
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String handlePayCallback(PayCallbackRequest request) {
        // 处理重复支付逻辑，可根据实际需求进行处理
        return PayCallbackType.DUPLICATE_PAYMENT.getDescription();
    }

}

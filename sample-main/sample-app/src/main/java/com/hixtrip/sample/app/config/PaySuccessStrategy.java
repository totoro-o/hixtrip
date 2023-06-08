package com.hixtrip.sample.app.config;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaySuccessStrategy implements PayCallbackStrategy{
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String handlePayCallback(PayCallbackRequest request) {
      orderDomainService.orderPaySuccess(request.getOrderId());
        return PayCallbackType.PAY_SUCCESS.getDescription();
    }

}

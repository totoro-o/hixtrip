package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayCallBackStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayBackCallSuccess implements PayCallBackStrategy {
    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public String getPayStatus() {
        return "1";
    }

    @Override
    public void payCallBack(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }
}

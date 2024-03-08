package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FailurePay implements PayStrategy {

    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String getStrategyCode() {
        return "failure";
    }
    @Override
    public void handleCallback(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }
}

package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DuplicatePay implements PayStrategy {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public String getStrategyCode() {
        return "duplicate";
    }

    @Override
    public void handleCallback(CommandPay commandPay) {
        throw new IllegalStateException ("重复支付，无法完成操作");
    }
}

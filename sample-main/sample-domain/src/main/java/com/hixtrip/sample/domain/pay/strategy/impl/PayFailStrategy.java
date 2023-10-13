package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付失败策略
 */
@Component("payFail")
public class PayFailStrategy implements PayStrategy {

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 支付失败
     * @param commandPay
     */
    @Override
    public void payCallback(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }

}

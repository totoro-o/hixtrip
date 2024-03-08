package com.hixtrip.sample.domain.component;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("success")
public class PaySuccessStrategy implements PayCallbackStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void payCallback(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }
}

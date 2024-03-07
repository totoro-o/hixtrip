package com.hixtrip.sample.infra.db.payment;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallbackFailServiceImpl implements CallbackService {

    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public Boolean pay(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
        payDomainService.payRecord(commandPay);
        System.out.println("支付失败");
        return true;
    }

    @Override
    public String getPayStatus() {
        return "fail";
    }
}

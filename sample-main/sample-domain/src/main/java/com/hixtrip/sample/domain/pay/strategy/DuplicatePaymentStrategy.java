package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DuplicatePaymentStrategy implements PayCallStrategy {
    @Autowired
    private PayDomainService payDomainService;

    @Override
    public void execute(String orderId) {
        CommandPay commandPay = CommandPay.builder()
                .orderId(orderId)
                .payStatus(PayStatus.DUPLICATE)
                .build();

        payDomainService.payRecord(commandPay);
    }
}

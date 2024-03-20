package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

@Component
public class PayDupStrategy implements PayStrategy{


    @Override
    public void handler(CommandPay commandPay) {
        throw new RuntimeException("订单重复");
    }
}

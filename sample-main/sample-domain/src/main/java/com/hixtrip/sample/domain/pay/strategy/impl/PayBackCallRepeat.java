package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayCallBackStrategy;
import org.springframework.stereotype.Component;

@Component
public class PayBackCallRepeat implements PayCallBackStrategy {
    @Override
    public String getPayStatus() {
        return "3";
    }

    @Override
    public void payCallBack(CommandPay commandPay) {

    }
}

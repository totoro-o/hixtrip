package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayStrategy {

    void handler(CommandPay commandPay);
}

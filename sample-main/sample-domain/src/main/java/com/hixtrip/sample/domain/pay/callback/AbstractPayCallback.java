package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public abstract class AbstractPayCallback implements PayCallbackInterface {

    public String payCallback(CommandPay commandPay) {

        // 留出公共方法

       return doPayCallback(commandPay);
    }

    protected abstract String doPayCallback(CommandPay commandPay);
}

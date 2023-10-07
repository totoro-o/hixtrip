package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayCallbackInterface {

    public String payCallback(CommandPay commandPay);
}

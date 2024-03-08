package com.hixtrip.sample.domain.component;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class PayCallbackContext {
    private PayCallbackStrategy payCallbackStrategy;

    public PayCallbackContext(PayCallbackStrategy payCallbackStrategy){
        this.payCallbackStrategy = payCallbackStrategy;
    }

    public void payCallback(CommandPay commandPay){
        this.payCallbackStrategy.payCallback(commandPay);
    }

}

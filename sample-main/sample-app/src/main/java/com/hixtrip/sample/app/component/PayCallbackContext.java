package com.hixtrip.sample.app.component;

import org.springframework.stereotype.Component;

@Component
public class PayCallbackContext {
    private PayCallbackStrategy payCallbackStrategy;

    public PayCallbackContext(PayCallbackStrategy payCallbackStrategy){
        this.payCallbackStrategy = payCallbackStrategy;
    }

    public String executePaySuccess(){
        return this.payCallbackStrategy.paySuccess();
    }

    public String executePayFail(){
        return this.payCallbackStrategy.payFail();
    }

    public String executeRePay(){
        return this.payCallbackStrategy.repay();
    }
}

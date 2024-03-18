package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.PaymentResult;

public abstract class PaymentStrategy {

    public abstract void execute(PaymentResult paymentResult);

}

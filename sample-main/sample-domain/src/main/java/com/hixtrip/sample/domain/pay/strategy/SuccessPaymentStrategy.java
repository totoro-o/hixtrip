package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.enums.PaymentStatusEnum;
import com.hixtrip.sample.domain.pay.model.PaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SuccessPaymentStrategy")
public class SuccessPaymentStrategy extends PaymentStrategy{

    @Autowired
    private PaymentContext paymentContext;

    @Autowired
    public void addStrategy(){
        paymentContext.addStrategy(PaymentStatusEnum.SUCCEED.getCode(), this);
    }

    @Override
    public void execute(PaymentResult paymentResult) {


    }

}

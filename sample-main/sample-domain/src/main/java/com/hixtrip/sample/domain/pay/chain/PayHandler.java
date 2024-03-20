package com.hixtrip.sample.domain.pay.chain;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayHandler {

    default void doHandler(CommandPay commandPay){
        throw new RuntimeException("没有匹配的方法回调");
    };
    Boolean canHandler(CommandPay commandPay);

    void setNextHandler(PayHandler payHandler);

}

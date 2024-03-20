package com.hixtrip.sample.domain.pay.chain;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;


@Component
public class PayDupHandler implements PayHandler{

    private PayHandler nextHandler;

    @Override
    public void doHandler(CommandPay commandPay) {

        if (canHandler(commandPay)){
            throw new RuntimeException("订单重复");
        }else {
            nextHandler.doHandler(commandPay);
        }
    }

    @Override
    public Boolean canHandler(CommandPay commandPay) {
        return PayStatusEnum.DUPLICATE.getValue().equals(commandPay.getPayStatus());
    }

    @Override
    public void setNextHandler(PayHandler payHandler) {
        this.nextHandler = payHandler;
    }
}

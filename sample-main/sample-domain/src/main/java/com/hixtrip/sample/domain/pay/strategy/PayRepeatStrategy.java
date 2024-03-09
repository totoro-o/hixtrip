package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayRepeatStrategy extends AbstractPayCallBackStrategy implements PayCallBackStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 重复
     * @param commandPay
     */
    @Override
    protected void doPayCallBack(CommandPay commandPay) {
        throw new RuntimeException("勿重复支付");
    }

    @Override
    public String getPayStatus() {
        return PayStatusEnum.REPEAT.getCode();
    }
}

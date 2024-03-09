package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaySuccessStrategy extends AbstractPayCallBackStrategy implements PayCallBackStrategy{

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 支付成功
     * @param commandPay
     */
    @Override
    protected void doPayCallBack(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }

    @Override
    public String getPayStatus() {
        return PayStatusEnum.SUCCESS.getCode();
    }
}

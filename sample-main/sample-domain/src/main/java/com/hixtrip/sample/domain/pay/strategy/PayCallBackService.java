package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayCallBackService {

    @Autowired
    private PayDomainService payDomainService;

    /**
     * 支付回调执行
     *
     * @param commandPay
     */
    public void payCallBack(CommandPay commandPay) {
        PayCallBackStrategy strategy = PayCallBackStrategyFactory.getStrategy(commandPay.getPayStatus());
        strategy.payCallBack(commandPay);
        payDomainService.payRecord(commandPay);
    }

}

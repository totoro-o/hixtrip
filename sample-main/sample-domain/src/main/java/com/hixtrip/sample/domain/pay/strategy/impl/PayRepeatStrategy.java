package com.hixtrip.sample.domain.pay.strategy.impl;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import org.springframework.stereotype.Component;

/**
 * 支付重复策略
 */
@Component("payRepeat")
public class PayRepeatStrategy implements PayStrategy {

    @Override
    public void payCallback(CommandPay commandPay) {
        throw new RuntimeException("订单已支付，请勿重复支付！");
    }

}

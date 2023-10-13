package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付策略接口
 */
public interface PayStrategy {

    void payCallback(CommandPay commandPay);

}

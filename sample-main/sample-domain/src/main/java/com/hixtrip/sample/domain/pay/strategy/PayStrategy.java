package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayStrategy {

    /**
     * 获取策略类型编码
     */
    String getStrategyCode();

    /**
     * 支付回调策略接口
     */
    void handleCallback(CommandPay commandPay);
}

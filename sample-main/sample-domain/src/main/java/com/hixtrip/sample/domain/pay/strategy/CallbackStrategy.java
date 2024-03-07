package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 12:55
 * 支付回调策略
 */
public interface CallbackStrategy {

    void payCallback(CommandPay commandPay);
}

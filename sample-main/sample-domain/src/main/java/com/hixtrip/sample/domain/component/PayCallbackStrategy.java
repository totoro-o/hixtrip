package com.hixtrip.sample.domain.component;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * 支付回调策略
 */
public interface PayCallbackStrategy {

    /**
     * 支付回调
     * @return
     */
    void payCallback(CommandPay commandPay);
}

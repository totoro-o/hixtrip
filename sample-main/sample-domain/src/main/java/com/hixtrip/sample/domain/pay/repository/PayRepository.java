package com.hixtrip.sample.domain.pay.repository;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface PayRepository {
    /**
     * 支付回调策略接口
     */
    void handleCallback(CommandPay commandPay);
}

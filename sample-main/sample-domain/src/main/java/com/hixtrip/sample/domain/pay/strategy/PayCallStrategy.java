package com.hixtrip.sample.domain.pay.strategy;

/**
 * 支付回调策略
 */
public interface PayCallStrategy {
    void execute(String orderId);
}

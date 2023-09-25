package com.hixtrip.sample.domain.pay.strategy;

/**
 * 支付回调上下文
 */
public class PayCallStrategyContext {
    private PayCallStrategy paymentStrategy;

    public PayCallStrategyContext(PayCallStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void execute(String orderId) {
        paymentStrategy.execute(orderId);
    }
}
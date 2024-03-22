package com.hixtrip.sample.app.paystrategy;


public class DuplicatePaymentStrategy implements PaymentStrategy {
    @Override
    public void handlePaymentResult(String orderId) {
        // 处理重复支付的逻辑
        System.out.println("重复支付！");
    }
}

package com.hixtrip.sample.app.paystrategy;

public interface PaymentStrategy {
    void handlePaymentResult(String orderId);
}

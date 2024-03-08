package com.hixtrip.sample.infra.payCallbackStrategy.impl;

import com.hixtrip.sample.infra.payCallbackStrategy.PayCallBackStrategy;

public class PayFailStrategy implements PayCallBackStrategy {

    @Override
    public void handle(String orderId) {
        // 支付失败的逻辑
    }
}

package com.hixtrip.sample.infra.payCallbackStrategy.impl;

import com.hixtrip.sample.infra.payCallbackStrategy.PayCallBackStrategy;

public class PaySuccessStrategy implements PayCallBackStrategy {

    @Override
    public void handle(String orderId) {
        // 支付成功的逻辑
    }
}

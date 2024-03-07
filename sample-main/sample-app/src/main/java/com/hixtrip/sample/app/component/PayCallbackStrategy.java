package com.hixtrip.sample.app.component;

import org.springframework.stereotype.Component;

/**
 * 支付回调策略
 */@Component
public interface PayCallbackStrategy {

    /**
     * 支付成功回调
     * @return
     */
    String paySuccess();

    /**
     * 支付失败回调
     * @return
     */
    String payFail();

    /**
     * 重复支付
     * @return
     */
    String repay();
}

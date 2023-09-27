package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付回调策略接口
 */
public interface PayCallBackStrategy {

    /**
     * 指定状态
     *
     * @return 支付状态
     */
    String getPayStatus();

    /**
     * 支付回调方法
     *
     * @param commandPay 回调参数
     */
    void payCallBack(CommandPay commandPay);
}

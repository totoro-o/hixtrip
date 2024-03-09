package com.hixtrip.sample.domain.pay.handler;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 策略接口
 */
public interface PayCallbackHandler {

    /**
     * 调用回调方法
     *
     * @param commandPay
     */
    void handlerCallback(CommandPay commandPay);

    /**
     * 获取支付状态
     *
     * @return PayStatusEnum
     */
    PayStatusEnum getPayStatus();
}

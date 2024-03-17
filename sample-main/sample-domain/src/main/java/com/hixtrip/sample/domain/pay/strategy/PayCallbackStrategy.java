package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付回调 策略
 */
public interface PayCallbackStrategy {

    /**
     * 支付状态
     *
     * @return
     */
    PayStatusEnum getPayStatus();

    /**
     * 处理
     *
     * @param commandPay
     */
    String handle(CommandPay commandPay);

    /**
     * 默认返回结果
     *
     * @return
     */
    default String getResult(CommandPay commandPay) {
        return "";
    }

}

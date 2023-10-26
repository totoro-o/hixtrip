package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.domain.constants.OrderPayCallbackStatusEnum;

/**
 * 支付回调策略
 */
public interface OrderPayCallbackStrategy {
    /**
     * 处理支付回调
     *
     * @param orderId 订单id
     */
    void handle(String orderId);

    /**
     * 获取支付回调状态
     */
    OrderPayCallbackStatusEnum getPayCallbackStatus();
}

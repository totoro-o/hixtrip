package com.hixtrip.sample.app.strategy.impl;

import com.hixtrip.sample.app.strategy.OrderPayCallbackStrategy;
import com.hixtrip.sample.domain.constants.OrderPayCallbackStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 重复支付回调策略
 */
@Slf4j
@Component
public class DuplicatePaymentStrategy implements OrderPayCallbackStrategy {
    @Override
    public void handle(String orderId) {
        log.warn("订单重复支付，订单id：{}", orderId);
        // ignore
    }

    @Override
    public OrderPayCallbackStatusEnum getPayCallbackStatus() {
        return OrderPayCallbackStatusEnum.DUPLICATE;
    }
}

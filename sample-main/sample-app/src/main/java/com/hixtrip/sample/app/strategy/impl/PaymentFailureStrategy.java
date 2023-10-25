package com.hixtrip.sample.app.strategy.impl;

import com.hixtrip.sample.app.strategy.OrderPayCallbackStrategy;
import com.hixtrip.sample.domain.constants.OrderPayCallbackStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 支付失败回调策略
 */
@Component
@RequiredArgsConstructor
public class PaymentFailureStrategy implements OrderPayCallbackStrategy {
    private final OrderDomainService orderDomainService;

    @Override
    public void handle(String orderId) {
        CommandPay payCommand = CommandPay.builder()
                .orderId(orderId)
                .payStatus(getPayCallbackStatus().getCode())
                .build();

        orderDomainService.orderPayFail(payCommand);
    }

    @Override
    public OrderPayCallbackStatusEnum getPayCallbackStatus() {
        return OrderPayCallbackStatusEnum.FAILURE;
    }
}
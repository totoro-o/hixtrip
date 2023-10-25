package com.hixtrip.sample.app.strategy.impl;

import com.hixtrip.sample.app.strategy.OrderPayCallbackStrategy;
import com.hixtrip.sample.domain.constants.OrderPayCallbackStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 支付成功回调策略
 */
@Service
@RequiredArgsConstructor
public class PaymentSuccessStrategy implements OrderPayCallbackStrategy {

    private final OrderDomainService orderDomainService;

    @Override
    public void handle(String orderId) {
        CommandPay commandPay = CommandPay.builder()
                .orderId(orderId)
                .payStatus(getPayCallbackStatus().getCode())
                .build();

        orderDomainService.orderPaySuccess(commandPay);
    }

    @Override
    public OrderPayCallbackStatusEnum getPayCallbackStatus() {
        return OrderPayCallbackStatusEnum.SUCCESS;
    }
}
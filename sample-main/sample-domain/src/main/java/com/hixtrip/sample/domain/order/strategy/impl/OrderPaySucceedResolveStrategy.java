package com.hixtrip.sample.domain.order.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.strategy.AbstractOrderPayCallbackResolveStrategy;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * <p> 订单支付回调成功处理策略
 *
 * @author airness
 * @since 2023/10/18 00:02
 */
@Component
public class OrderPaySucceedResolveStrategy extends AbstractOrderPayCallbackResolveStrategy {

    public OrderPaySucceedResolveStrategy(OrderDomainService orderDomainService) {
        super(orderDomainService);
    }

    @Override
    protected void commandResolve(OrderDomainService command, CommandPay dto) {
        try {
            command.orderPaySuccess(dto);
        } catch (RuntimeException e) {
            // 重复支付
            System.out.println("模拟处理退款");
        }
    }

    @Override
    public boolean shouldResolve(@NotNull CommandPay dto) {
        return dto.isStatusValid(CommandPay.PayStatus.PAID);
    }
}


package com.hixtrip.sample.domain.order.strategy.impl;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.strategy.AbstractOrderPayCallbackResolveStrategy;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * <p> 订单支付失败回调处理策略
 *
 * @author airness
 * @since 2023/10/18 10:16
 */
@Component
public class OrderPayFailedResolveStrategy extends AbstractOrderPayCallbackResolveStrategy {

    public OrderPayFailedResolveStrategy(OrderDomainService orderDomainService) {
        super(orderDomainService);
    }

    @Override
    protected void commandResolve(OrderDomainService command, CommandPay dto) {
        command.orderPayFail(dto);
    }

    @Override
    public boolean shouldResolve(@NotNull CommandPay dto) {
        return dto.isStatusValid(CommandPay.PayStatus.CANCEL) ||
                dto.isStatusValid(CommandPay.PayStatus.TIMEOUT);
    }

}

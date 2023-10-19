package com.hixtrip.sample.domain.order.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

/**
 * <p> 抽象订单支付回调策略，进一步规范订单回调处理策略行为
 *
 * @author airness
 * @since 2023/10/18 10:13
 */
public abstract class AbstractOrderPayCallbackResolveStrategy implements StatusResolveStrategy<CommandPay> {

    @Getter(AccessLevel.PROTECTED)
    private final OrderDomainService command;


    protected AbstractOrderPayCallbackResolveStrategy(OrderDomainService orderDomainService) {
        Assert.notNull(orderDomainService, "订单域服务不可为null");
        this.command = orderDomainService;
    }


    protected abstract void commandResolve(OrderDomainService command, CommandPay dto) throws Exception;


    @Override
    public @NotNull ResolveResult resolve(CommandPay dto) throws Exception {
        commandResolve(this.command, dto);
        return ResolveResult.RESOLVED;
    }
}

package com.hixtrip.sample.domain.order.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategy;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategyFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * <p> 订单回调状态处理策略工厂；
 * <p> 用于处理策略模式
 *
 * @author airness
 * @since 2023/10/17 23:32
 */
public class OrderCallbackStatusResolveStrategyFactory implements StatusResolveStrategyFactory<CommandPay, StatusResolveStrategy<CommandPay>> {

    private final Set<StatusResolveStrategy<CommandPay>> commandStrategies;

    public OrderCallbackStatusResolveStrategyFactory(Collection<StatusResolveStrategy<CommandPay>> commandStrategies) {
        this.commandStrategies = new LinkedHashSet<>(commandStrategies);
    }


    @Override
    public @Nullable Collection<StatusResolveStrategy<CommandPay>> getStrategies() {
        return commandStrategies;
    }
}

package com.hixtrip.sample.domain.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * <p> 状态处理策略抽象工厂
 *
 * @author airness
 * @since 2023/10/17 23:26
 */
public interface StatusResolveStrategyFactory<DO, T extends StatusResolveStrategy<DO>> {


    /**
     * 获取工厂中所有策略
     *
     * @return 返回策略集合
     */
    @Nullable Collection<T> getStrategies();

    /**
     * 工厂的策略处理方法，仅处理首次匹配的受支持的策略，不会进行多次匹配，若未匹配任何策略，将返回NON_MATCHES
     * @param entry 待处理实体
     * @return 返回处理结果
     */
    default @NotNull StatusResolveStrategy.ResolveResult resolve(DO entry) throws RuntimeException{
        return Optional.ofNullable(getStrategies()).orElse(Collections.emptyList())
                .stream()
                .filter(strategy -> strategy.shouldResolve(entry))
                .findFirst()
                .map(s -> {
                    try {
                        return s.resolve(entry);
                    } catch (Exception e) {
                        // 打印日志
                        return StatusResolveStrategy.ResolveResult.FAILED;
                    }
                })
                .orElseGet(() -> {
                    // 记录entry日志
                    return StatusResolveStrategy.ResolveResult.NON_MATCHES;
                });
    }
}

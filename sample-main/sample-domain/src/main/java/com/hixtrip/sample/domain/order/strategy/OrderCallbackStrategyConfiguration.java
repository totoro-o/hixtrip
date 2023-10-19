package com.hixtrip.sample.domain.order.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategy;
import com.hixtrip.sample.domain.strategy.StatusResolveStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p> 订单回调状态处理策略模式Java配置类
 *
 * @author airness
 * @since 2023/10/17 23:31
 */
@Configuration
public class OrderCallbackStrategyConfiguration {


    /**
     * 配置订单回调状态的策略工厂
     * @param strategies 注入策略对象(不存在Bean将在启动时报错)
     * @return 返回初始化的策略工厂
     * @implNote 可根据实际情况替换处理工厂逻辑
     */
    @Bean
    public StatusResolveStrategyFactory<CommandPay, StatusResolveStrategy<CommandPay>> orderCallbackStatusResolveStrategyFactory(List<StatusResolveStrategy<CommandPay>> strategies) {
        return new OrderCallbackStatusResolveStrategyFactory(strategies);
    }
}

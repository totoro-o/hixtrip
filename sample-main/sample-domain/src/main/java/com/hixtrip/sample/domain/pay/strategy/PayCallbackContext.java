package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付回调上下文
 */
@Component
public class PayCallbackContext {

    private final Map<PayStatusEnum, PayCallbackStrategy> payCallbackStrategyMap;

    private final PayCallbackStrategy payCallbackStrategy = new PayCallbackStrategy() {
        @Override
        public PayStatusEnum getPayStatus() {
            return null;
        }

        @Override
        public String handle(CommandPay commandPay) {
            throw new RuntimeException("没有找到匹配的回调处理逻辑");
        }
    };

    /**
     * 默认构造器
     *
     * @param payCallbackStrategies
     */
    public PayCallbackContext(List<PayCallbackStrategy> payCallbackStrategies) {
        this.payCallbackStrategyMap = new HashMap<>();
        payCallbackStrategies.forEach(payCallbackStrategy -> payCallbackStrategyMap.put(payCallbackStrategy.getPayStatus(), payCallbackStrategy));
    }

    /**
     * 处理
     *
     * @param commandPay
     * @return
     */
    public String handle(CommandPay commandPay) {
        PayStatusEnum payStatusEnum = PayStatusEnum.find(commandPay.getPayStatus());
        Assert.notNull(payStatusEnum, "未知回调支付类型");

        PayCallbackStrategy payCallbackStrategy = this.payCallbackStrategyMap.getOrDefault(payStatusEnum, this.payCallbackStrategy);
        return payCallbackStrategy.handle(commandPay);
    }
}

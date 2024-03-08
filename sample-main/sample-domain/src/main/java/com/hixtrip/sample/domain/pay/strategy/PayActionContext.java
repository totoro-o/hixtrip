package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PayActionContext {


    private static final Map<String, PayStrategy> strategyMap = new HashMap<>();

    @Autowired
    public PayActionContext(List<PayStrategy> strategyList) {
        for (PayStrategy payStrategy : strategyList) {
            strategyMap.put(payStrategy.getStrategyCode(), payStrategy);
        }
    }


    public void processPaymentCommand(CommandPay commandPay) {
        PayStrategy strategy = strategyMap.get(commandPay.getPayStatus());
        if (strategy != null) {
            strategy.handleCallback(commandPay);
        } else {
            // 处理未知或无效的状态
            throw new IllegalStateException("Invalid strategy");
        }
    }
}

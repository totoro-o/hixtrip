package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PayStrategySelector {

    private final Map<String, PayStrategy> strategyMap;

    @Autowired
    public PayStrategySelector(List<PayStrategy> payStrategies) {
        this.strategyMap = new HashMap<>();
        for (PayStrategy strategy : payStrategies) {
            for (PayStatusEnum status : PayStatusEnum.values()) {
                if (strategy.getClass().equals(status.getStrategyClass())) {
                    strategyMap.put(status.getValue(), strategy);
                    break;
                }
            }
        }
    }

    public PayStrategy getStrategy(String statusValue) {
        PayStrategy payStrategy = strategyMap.get(statusValue);
        Assert.notNull(payStrategy,"未找到匹配策略");
        return payStrategy;
    }

}

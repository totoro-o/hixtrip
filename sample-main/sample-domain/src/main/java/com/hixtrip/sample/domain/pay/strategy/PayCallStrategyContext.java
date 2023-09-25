package com.hixtrip.sample.domain.pay.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayCallStrategyContext {

    private final Map<String, PayCallStrategy> strategyMap = new ConcurrentHashMap<>();


    @Autowired
    public PayCallStrategyContext(Map<String, PayCallStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach((k, v) -> this.strategyMap.put(k, v));
    }

    public PayCallStrategy getInstance(String label) {
        String beanName = PayCallStrategyEnum.getByLabel(label).getBeanName();
        return this.getInstanceByBeanName(beanName);
    }

    private PayCallStrategy getInstanceByBeanName(String beanName) {
        return strategyMap.get(beanName);
    }

}
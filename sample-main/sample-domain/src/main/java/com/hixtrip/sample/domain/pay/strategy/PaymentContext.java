package com.hixtrip.sample.domain.pay.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentContext {

    private final Map<Integer, PaymentStrategy> strategyMap = new ConcurrentHashMap<>();

    public void addStrategy(Integer code, PaymentStrategy strategy){
        strategyMap.put(code, strategy);
    }

    public PaymentStrategy getStrategy(Integer code){
        return strategyMap.getOrDefault(code, null);
    }

}

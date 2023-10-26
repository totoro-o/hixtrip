package com.hixtrip.sample.app.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderPayCallbackStrategyFactory implements InitializingBean {

    private final Map<String, OrderPayCallbackStrategy> strategiesMap = new HashMap<>();

    @Autowired
    private List<OrderPayCallbackStrategy> strategyList;


    public Optional<OrderPayCallbackStrategy> getStrategy(String status) {
        return Optional.ofNullable(strategiesMap.get(status));
    }

    @Override
    public void afterPropertiesSet() {
        strategyList.forEach(it -> {
            strategiesMap.put(it.getPayCallbackStatus().getCode(), it);
        });
    }
}

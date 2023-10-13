package com.hixtrip.sample.domain.pay.strategy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支付策略上下文
 * 通过@Service定义注册到容器，SpringBoot会自动获取实现PayStrategy接口的实现类列表
 * 实现类设置组件名，后续会通过组件名来获取对应的实现类
 */
@Service
public class PayStrategyContext {

    private final Map<String, PayStrategy> strategyServiceMap = new ConcurrentHashMap<>();

    public PayStrategyContext(Map<String, PayStrategy> strategyMap) {
        strategyMap.forEach(this.strategyServiceMap::put);
    }

    public PayStrategy getResource(String payType) {
        return strategyServiceMap.get(payType);
    }

}

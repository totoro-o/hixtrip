package com.hixtrip.sample.domain.pay.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付回调策略工厂
 */
@Component
public class PayCallBackStrategyFactory implements ApplicationContextAware {

    private static Map<String, PayCallBackStrategy> handleMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PayCallBackStrategy> handleBeanMap = applicationContext.getBeansOfType(PayCallBackStrategy.class);
        if (!handleBeanMap.isEmpty()) {
            //根据不同回调组装不同的bean
            handleBeanMap.forEach((name, bean) -> handleMap.put(bean.getPayStatus(), bean));
        }
    }

    public static PayCallBackStrategy getStrategy(String payStatus) {
        return handleMap.get(payStatus);
    }

}



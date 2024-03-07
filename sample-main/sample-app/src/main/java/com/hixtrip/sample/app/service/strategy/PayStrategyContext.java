package com.hixtrip.sample.app.service.strategy;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hixtrip.sample.domain.enums.PayStatusEnums;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayStrategyContext {

    private final Map<String, PayStrategy> strategyMap = new ConcurrentHashMap<>();
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        strategyMap.putAll(applicationContext.getBeansOfType(PayStrategy.class));
    }


    public String getPayStrategy(CommandPayDTO commandPayDTO) {
        String beanName = PayStatusEnums.getByCode(commandPayDTO.getPayStatus()).getStrategy();
        return this.getInstanceByBeanName(beanName).pay(commandPayDTO);
    }

    private PayStrategy getInstanceByBeanName(String beanName) {
        if (!StringUtils.isEmpty(beanName)) {
            return strategyMap.get(beanName);
        }
        return null;
    }
}

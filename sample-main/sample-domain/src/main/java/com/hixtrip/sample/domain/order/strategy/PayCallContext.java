package com.hixtrip.sample.domain.order.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayCallContext implements ApplicationContextAware {

    Map<String, IPayCall> payCallMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] beanNames = applicationContext.getBeanNamesForType(IPayCall.class);
        for(String beanName : beanNames){
            IPayCall bean = (IPayCall)applicationContext.getBean(beanName);
            String status = bean.payStatus();
            payCallMap.put(status, bean);
        }
    }

    public void payCallback(CommandPay commandPay){
        String payStatus = commandPay.getPayStatus();
        IPayCall payCall = payCallMap.get(payStatus);
        payCall.payCall(commandPay);
    }

}

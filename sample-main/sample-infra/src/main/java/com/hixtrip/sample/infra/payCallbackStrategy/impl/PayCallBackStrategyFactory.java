package com.hixtrip.sample.infra.payCallbackStrategy.impl;

import com.hixtrip.sample.infra.payCallbackStrategy.PayCallBackStrategy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Component
public class PayCallBackStrategyFactory {

    private static final Map<String, PayCallBackStrategy> payCallBackStrategyMap = new HashMap<>();


    static {
        payCallBackStrategyMap.put("0",new PayFailStrategy());
        payCallBackStrategyMap.put("1",new PaySuccessStrategy());
    }

    public PayCallBackStrategy getInstance(String status){
        if(!payCallBackStrategyMap.containsKey(status)){
            throw new RuntimeException("支付回调状态异常");
        }
        return payCallBackStrategyMap.get(status);
    }
}

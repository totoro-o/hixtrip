package com.hixtrip.sample.infra.payCallbackStrategy.impl;

import com.hixtrip.sample.infra.payCallbackStrategy.PayCallBackStrategy;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class PayCallBackStrategyFactory {

    private static Map<String, PayCallBackStrategy> payCallBackStrategyMap = new HashMap<>();

    @PostConstruct
    private void init(){
        payCallBackStrategyMap.put("0",new PayFailStrategy());
        payCallBackStrategyMap.put("1",new PaySuccessStrategy());
    }

    public static PayCallBackStrategy getInstance(String status){
        return payCallBackStrategyMap.get(status);
    }
}

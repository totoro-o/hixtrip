package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayCallBackStrategyContext {


    private Map<String, PayCallBackStrategy> payCallBack;

    private final Map<String, PayCallBackStrategy> typeOfPayCallBack = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.payCallBack.values().forEach(item -> this.typeOfPayCallBack.put(item.getPayStatus(), item));
    }

    public void payCallBack(CommandPay commandPay) {
        PayCallBackStrategy callBackStrategy = this.typeOfPayCallBack.get(commandPay.getPayStatus());
        if (callBackStrategy != null) {
            callBackStrategy.payCallBack(commandPay);
        }
    }


}

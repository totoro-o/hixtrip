package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.client.order.vo.PayCallbaskVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PayCallbackContext {
    private final Map<String, PayCallbackStrategy> strategies;

    @Autowired
    public PayCallbackContext(Map<String, PayCallbackStrategy> strategies) {
        this.strategies = strategies;
    }

    public PayCallbaskVO executeStrategy(String payType, CommandPay commandPay) {
        PayCallbackStrategy payCallbackStrategy = strategies.get(payType);
        if (payCallbackStrategy != null) {
            return payCallbackStrategy.payCallback(commandPay);
        }
        return null;
    }
}

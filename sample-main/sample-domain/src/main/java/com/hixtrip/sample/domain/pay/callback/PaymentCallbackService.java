package com.hixtrip.sample.domain.pay.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.repository.PayRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuYuDe
 */
@Service
public class PaymentCallbackService {
    private final Map<String, PayRepository> strategies;

    @Autowired
    public PaymentCallbackService(Map<String, PayRepository> strategies) {
        this.strategies = strategies;
    }
    public void processCallback(String status, CommandPay commandPay) {
        PayRepository strategy = strategies.get(status);
        if (strategy != null) {
            strategy.handleCallback(commandPay);
        } else {
            throw new IllegalArgumentException("调用失败: " + status);
        }
    }
}

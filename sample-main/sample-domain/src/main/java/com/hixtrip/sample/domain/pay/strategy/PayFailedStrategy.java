package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.event.PayFailedEvent;
import com.hixtrip.sample.domain.pay.event.source.PayFailedEventSource;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 12:58
 * 支付失败策略
 */
public class PayFailedStrategy implements CallbackStrategy {
    @Override
    public void payCallback(CommandPay commandPay) {
        // 处理完支付域的相关逻辑后，发送支付失败事件
        PayFailedEventSource source = PayFailedEventSource.builder()
                .commandPay(commandPay)
                .build();

        new PayFailedEvent(source).publish();
    }
}

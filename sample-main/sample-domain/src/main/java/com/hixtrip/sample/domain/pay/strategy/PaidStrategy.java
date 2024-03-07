package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.event.PaidEvent;
import com.hixtrip.sample.domain.pay.event.source.PaidEventSource;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 12:57
 * 支付成功策略
 */
public class PaidStrategy implements CallbackStrategy {
    @Override
    public void payCallback(CommandPay commandPay) {
        // 处理完支付域的相关逻辑后，发送支付成功事件
        PaidEventSource source = PaidEventSource.builder()
                .commandPay(commandPay)
                .build();

        new PaidEvent(source).publish();
    }
}

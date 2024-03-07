package com.hixtrip.sample.domain.pay.event;

import com.hixtrip.sample.domain.pay.event.source.PayFailedEventSource;
import lombok.Getter;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:13
 * 支付失败事件
 */
@Getter
public class PayFailedEvent extends BaseEvent {

    private final PayFailedEventSource source;

    public PayFailedEvent(PayFailedEventSource source) {
        super(source);
        this.source = source;
    }
}

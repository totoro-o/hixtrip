package com.hixtrip.sample.domain.pay.event;

import com.hixtrip.sample.domain.pay.event.source.PaidEventSource;
import lombok.Getter;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:12
 * 支付成功事件
 */
@Getter
public class PaidEvent extends BaseEvent {

    private final PaidEventSource source;
    public PaidEvent(PaidEventSource source) {
        super(source);
        this.source = source;
    }
}

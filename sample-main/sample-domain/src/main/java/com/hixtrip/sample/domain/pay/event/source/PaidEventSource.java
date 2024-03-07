package com.hixtrip.sample.domain.pay.event.source;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.Builder;
import lombok.Getter;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:12
 * 支付成功事件源
 */
@Getter
@Builder
public class PaidEventSource implements BaseEventSource {

    /**
     * 支付域
     */
    private CommandPay commandPay;
}

package com.hixtrip.sample.domain.pay.event.source;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.Builder;
import lombok.Getter;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:13
 * 支付失败事件源
 */
@Getter
@Builder
public class PayFailedEventSource implements BaseEventSource {

    /**
     * 支付域
     */
    private CommandPay commandPay;
}

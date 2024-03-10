package com.hixtrip.sample.app.strategy.impl;

import com.hixtrip.sample.app.strategy.PayCallbackStrategy;
import com.hixtrip.sample.client.order.vo.PayCallbaskVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

@Component("repeatedPayStrategy")
public class RepeatedPayStrategy implements PayCallbackStrategy {
    @Override
    public PayCallbaskVO payCallback(CommandPay commandPay) {
        // TODO: 2024/3/9 重复支付安排退款
        return PayCallbaskVO.builder().id(commandPay.getOrderId()).resultStatus("success").resultMsg("").build();
    }
}

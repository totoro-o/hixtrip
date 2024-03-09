package com.hixtrip.sample.domain.pay.handler.impl;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.handler.PayCallbackHandler;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 重复支付处理器
 */
@Service
public class DuplicatePayHandler implements PayCallbackHandler {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void handlerCallback(CommandPay commandPay) {
        // 重复支付
        orderDomainService.orderPayDuplicate(commandPay);
    }

    @Override
    public PayStatusEnum getPayStatus() {
        return PayStatusEnum.PAY_DUPLICATE;
    }
}

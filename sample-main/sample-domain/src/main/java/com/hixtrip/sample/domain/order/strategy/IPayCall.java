package com.hixtrip.sample.domain.order.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付回调抽象策略类
 */
public interface IPayCall {

    void payCall(CommandPay commandPay);

    String payStatus();
}

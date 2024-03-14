package com.hixtrip.sample.infra.handler;


import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付处理服务
 */
public interface PaymentHandleService<T, R extends CommandPay> {

    /**
     * 处理逻辑 下单/回调
     * @param order
     * @return
     */
    R process(T order);

    Boolean checkSign(T order);
}

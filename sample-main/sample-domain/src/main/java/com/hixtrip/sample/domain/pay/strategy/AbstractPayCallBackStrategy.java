package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 支付回调策略接口
 */
public abstract class AbstractPayCallBackStrategy implements PayCallBackStrategy {

    @Override
    public void payCallBack(CommandPay commandPay){
        doPayCallBack(commandPay);
    }

    protected  abstract void doPayCallBack(CommandPay commandPay);
}

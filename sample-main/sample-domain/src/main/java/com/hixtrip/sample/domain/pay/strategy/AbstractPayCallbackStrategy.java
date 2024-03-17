package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * 抽象类
 */
public abstract class AbstractPayCallbackStrategy implements PayCallbackStrategy {

    /**
     * 模板方法
     *
     * @param commandPay
     * @return
     */
    @Override
    public String handle(CommandPay commandPay) {
        this.doHandle(commandPay);
        return this.getResult(commandPay);
    }

    /**
     * 抽象方法
     *
     * @param commandPay
     */
    public abstract void doHandle(CommandPay commandPay);
}

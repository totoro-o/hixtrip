package com.hixtrip.sample.infra.handler;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.CommandPayDO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/*
 * 支付基础类 自动绑定参数和对应service
 */
public abstract class BasePayBindService<T, R extends CommandPay> implements PaymentHandleService<T, R>{

    @Autowired
    IPayStrategyService payStrategyService;


    /**
     * 指定绑定的参数类
     */
    public abstract Class<T> getbindParamClass();


    @PostConstruct
    public void bindService() {
        payStrategyService.bindPayService(getbindParamClass(),this);
    }

}

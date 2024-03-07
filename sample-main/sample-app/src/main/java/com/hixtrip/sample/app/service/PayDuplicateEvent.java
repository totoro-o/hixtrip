package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.constants.PayStatusConstant;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Component(value = PayStatusConstant.DUPLICATE)
public class PayDuplicateEvent implements PayEvent {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void payCallback(CommandPay commandPay) {
        System.out.println("重复支付");
    }
}

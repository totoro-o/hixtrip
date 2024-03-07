package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    void createOrder(Order order);
    String orderPaySuccess(CommandPay commandPay);
    String orderPayFail(CommandPay commandPay);
}

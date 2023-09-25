package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    Order createOrder(Order order);

    void updatePayInfo(CommandPay commandPay);

    Order getById(String id);
}

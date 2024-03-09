package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    void createOrder(Order order);

    void updateOrder(CommandPay commandPay, PayStatusEnum payStatusEnum);

    Order getOrderById(String orderId);
}

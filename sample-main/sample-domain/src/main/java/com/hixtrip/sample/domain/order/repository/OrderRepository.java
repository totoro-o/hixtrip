package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    String createOrder(Order order);

    void updateOrder(Order order);

    Order qryOrderById(String orderId);
}

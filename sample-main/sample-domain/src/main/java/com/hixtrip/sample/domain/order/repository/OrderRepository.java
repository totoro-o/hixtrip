package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    Order find(String orderId);

    void createOrder(Order order);

    void updateOrderById(Order order);
}

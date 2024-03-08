package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    void save(Order order);

    Order getByOrderId(String orderId);

    void updateOrder(Order order);
}

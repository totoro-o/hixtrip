package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

import java.util.Map;

/**
 *
 */
public interface OrderRepository {

    Order createOrder(Order order);

    Map<Object, Object> getOrder(String orderId);

    void delOrderById(String orderId);
}

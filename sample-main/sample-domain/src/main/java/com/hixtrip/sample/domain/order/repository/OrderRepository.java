package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    Order createOrder(Order order);

    void orderPaySuccess(String orderId);

    void orderPayFail(String orderId);
}

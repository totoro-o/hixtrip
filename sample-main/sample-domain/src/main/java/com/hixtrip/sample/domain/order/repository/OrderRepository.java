package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

public interface OrderRepository {

    Order addOrder(Order order);

    Order getOrderByOrderNumber(String orderNumber);

    void update(Order order);
}

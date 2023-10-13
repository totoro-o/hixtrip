package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    void saveOrderInfo(Order order);

    void updateOrderPayStatus(String orderId,String payStatus);
}

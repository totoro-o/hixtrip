package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;

/**
 *
 */
public interface OrderRepository {

    void createOrder(OrderDO order);


    void updateOrderById(OrderDO order);


    OrderDO queryById(String orderId);
}

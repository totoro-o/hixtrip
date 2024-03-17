package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    /**
     * 创建订单
     *
     * @param order
     */
    void createOrder(Order order);

    /**
     * getByOrderId
     *
     * @param orderId
     * @return
     */
    Order getByOrderId(String orderId);

    /**
     * update by id
     *
     * @param order
     */
    void updateById(Order order);
}

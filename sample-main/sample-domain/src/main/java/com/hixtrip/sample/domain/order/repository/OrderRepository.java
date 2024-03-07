package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {

    /**
     * 通过订单id查询订单信息
     *
     * @param orderId 订单id
     * @return
     */
    Order getById(String orderId);

    /**
     * 保存订单信息
     *
     * @param order 订单信息
     * @return
     */
    Boolean saveOrder(Order order);

    /**
     * 更新订单
     *
     * @param order 订单信息
     * @return
     */
    Boolean updateOrder(Order order);
}

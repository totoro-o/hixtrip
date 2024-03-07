package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

import java.util.Optional;

/**
 * 订单
 */
public interface OrderRepository {

    /**
     * 通过订单id查询订单信息
     * @param orderId
     * @return
     */
   Optional<Order> getById(String orderId);

    /**
     * 保存订单信息
     * @param order
     * @return
     */
    Boolean saveOrder(Order order);

    /**
     * 更新订单
     * @param order
     * @return
     */
    Boolean updateOrder(Order order);
}

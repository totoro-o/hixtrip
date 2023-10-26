package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    /**
     * 保存订单
     *
     * @param order 订单
     */
    boolean save(Order order);

    /**
     * 根据订单id获取订单
     *
     * @param orderId 订单id
     * @return 订单
     */
    Order getById(String orderId);

    /**
     * 根据 id 更新订单
     * @param order 订单
     * @return 是否更新成功
     */
    boolean updateById(Order order);
}

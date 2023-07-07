package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.domain.order.model.Order;

public interface OrderMapper {
    /**
     * 添加订单
     * @param order
     * @return
     */
    Order addOrder(Order order);

    /**
     * 通过订单号获取订单
     * @param orderNumber
     * @return
     */
    Order getOrderByOrderNumber(String orderNumber);

    /**
     * 更新订单
     * @param order
     */
    void update(Order order);
}

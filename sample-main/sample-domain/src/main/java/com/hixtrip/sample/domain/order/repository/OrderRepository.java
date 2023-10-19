package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {


    /**
     * 插入订单
     * @param order 订单领域模型
     */
    void insert(Order order);


    /**
     * 订单模型更新
     * @param order 订单领域模型
     */
    void update(Order order);

    /**
     * 查询订单
     * @param id 订单
     * @return 域对象
     */
    Order findById(String id);
}

package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    Integer save(Order order);
}

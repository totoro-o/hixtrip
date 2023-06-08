package com.hixtrip.sample.domain.order.repository;


import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    void createOrder(Order order);

    void updateOrder(Order order);

    Order findByOrderNumber(String orderNumber);

    String findOrderNumberByOrderId(Long id);
}

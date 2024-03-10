package com.hixtrip.sample.infra.db;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Order createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderDO.setCreateTime(LocalDateTime.now());
        orderDO.setUpdateTime(LocalDateTime.now());
        int insert = orderMapper.insert(orderDO);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public Order getOrderById(String id) {
        return OrderDOConvertor.INSTANCE.doToDomain(orderMapper.selectById(id));
    }

    @Override
    public void updateOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderMapper.updateById(orderDO);
    }
}

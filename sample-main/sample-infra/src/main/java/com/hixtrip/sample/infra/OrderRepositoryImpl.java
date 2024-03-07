package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 类说明
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getById(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        if (orderDO == null) {
            return null;
        }
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public Boolean saveOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderDO.setCreateTime(LocalDateTime.now());
        return orderMapper.insert(orderDO) == 1;
    }

    @Override
    public Boolean updateOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        return orderMapper.updateById(orderDO) == 1;
    }
}

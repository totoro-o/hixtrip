package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Order find(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public void createOrder(Order order) {
        orderMapper.insert(OrderDOConvertor.INSTANCE.domainToDO(order));
    }

    @Override
    public void updateOrderById(Order order) {
        orderMapper.updateById(OrderDOConvertor.INSTANCE.domainToDO(order));
    }
}

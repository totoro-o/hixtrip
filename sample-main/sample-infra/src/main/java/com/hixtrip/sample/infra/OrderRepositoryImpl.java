package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.oderToOrderDo(order);
        orderMapper.creatOrder(orderDO);
        return orderDO.getId();
    }

    @Override
    public void updateOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.oderToOrderDo(order);
        orderMapper.updateOrder(orderDO);
    }

    @Override
    public Order qryOrderById(String orderId) {
        OrderDO orderDO = orderMapper.qryOrderById(orderId);
        return OrderDOConvertor.INSTANCE.oderDOToOrder(orderDO);
    }
}

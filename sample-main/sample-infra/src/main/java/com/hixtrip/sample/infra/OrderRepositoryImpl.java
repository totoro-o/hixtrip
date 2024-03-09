package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private InventoryDomainService inventoryDomainService;


    @Override
    public void createOrder(OrderDO order) {
        orderMapper.insert(order);
    }


    @Override
    public void updateOrderById(OrderDO order) {
        orderMapper.updateById(order);
    }

    @Override
    public OrderDO queryById(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        return orderDO;
    }
}

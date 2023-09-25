package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
    public Order createOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(order, orderDO);
        orderMapper.insertOrder(orderDO);

        order.setId(orderDO.getId().toString());
        return order;
    }

    @Override
    public void updatePayInfo(CommandPay commandPay) {
        OrderDO orderDO = OrderDO.builder()
                .id(Long.parseLong(commandPay.getOrderId()))
                .payTime(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()))
                .payStatus(commandPay.getPayStatus())
                .build();

        orderMapper.updateOrderPay(orderDO);
    }

    @Override
    public Order getById(String id) {
        return orderMapper.getById(id);
    }
}

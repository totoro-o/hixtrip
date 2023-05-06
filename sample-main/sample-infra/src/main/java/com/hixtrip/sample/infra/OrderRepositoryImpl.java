package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Order createOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(order, orderDO);
        String orderNo = getOrderNo();
        orderDO.setOrderNo(orderNo);
        orderDO.setPayStatus(Order.PayStatusEnum.WAIT_PAY.getCode());
        orderMapper.save(orderDO);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public int update(Order order) {
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(order, orderDO);
        return orderMapper.save(orderDO);
    }

    private String getOrderNo() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

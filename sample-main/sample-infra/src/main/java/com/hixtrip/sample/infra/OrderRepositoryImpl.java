package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl implements OrderRepository {


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doFromOrder(order);
        orderMapper.insert(orderDO);
        return OrderDOConvertor.INSTANCE.doToOrder(orderDO);
    }

    @Override
    public Order editOrderPayStatus(String orderId, String payStatus) {
        OrderDO orderDO = orderMapper.getOne(orderId);
        orderDO.setPayStatus(payStatus);
        orderMapper.updateById(orderDO);
        return OrderDOConvertor.INSTANCE.doToOrder(orderDO);
    }


}

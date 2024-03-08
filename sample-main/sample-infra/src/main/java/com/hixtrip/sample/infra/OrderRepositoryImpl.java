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
    public void createOrder(Order order){
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderMapper.insert(orderDO);
    }

    @Override
    public Order getOrderByOrderId(String orderId){
        OrderDO orderDO = orderMapper.selectById(orderId);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public boolean orderToSuccess(String orderId,String payId, String orderStatus, String payStatus) {
        return orderMapper.orderToSuccess(orderId, payId, orderStatus, payStatus);
    }

    @Override
    public boolean isNoPay(String orderId, String payStatus) {
        return orderMapper.isNoPay(orderId, payStatus);
    }
}

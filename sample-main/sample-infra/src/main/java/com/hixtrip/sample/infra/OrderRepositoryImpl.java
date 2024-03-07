package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOrderById(String orderId) {
//        OrderDO orderDO = orderMapper.selectById(orderId);
//        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
        //测试数据
        Order order = new Order();
        order.setOrderId("1");
        order.setPayStatus("wait");
        order.setSkuId("sku001");
        order.setAmount(10);
        return order;
    }

    @Override
    public void createOrder(Order order) {
        //this.orderMapper.insert(OrderDOConvertor.INSTANCE.domainToDO(order));
    }

    @Override
    public void updateOrder(Order order) {
        //this.orderMapper.updateById(OrderDOConvertor.INSTANCE.domainToDO(order));
    }
}

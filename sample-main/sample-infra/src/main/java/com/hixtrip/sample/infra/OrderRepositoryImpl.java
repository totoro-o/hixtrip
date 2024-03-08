package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDo;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lmk
 * @create 2024/3/7 16:38
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int createOrder(Order order) {
        OrderDo orderDo = OrderDOConvertor.INSTANCE.domainTODo(order);
        return orderMapper.insert(orderDo);
    }

    @Override
    public void updateOrder(CommandPay commandPay) {
        OrderDo orderDo = OrderDo.builder().payStatus(commandPay.getPayStatus()).id(commandPay.getOrderId()).build();
        orderMapper.updateById(orderDo);
    }
}

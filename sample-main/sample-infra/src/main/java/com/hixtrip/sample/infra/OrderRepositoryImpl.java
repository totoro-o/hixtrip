package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 09:14
 * 订单领域资源层实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    public Optional<Order> getById(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        if(Objects.nonNull(orderDO)) {
            return Optional.of(OrderDOConvertor.INSTANCE.convert(orderDO));
        }
        return Optional.empty();
    }

    @Override
    public Boolean saveOrder(Order order) {

        OrderDO orderDO = OrderDOConvertor.INSTANCE.convert(order);
        int result = orderMapper.insert(orderDO);
        return result != 0;
    }

    @Override
    public Boolean updateOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.convert(order);
        int result = orderMapper.updateById(orderDO);
        return result != 0;
    }
}

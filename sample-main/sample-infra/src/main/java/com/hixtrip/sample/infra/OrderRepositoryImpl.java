package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderMapper orderMapper;

    @Override
    public boolean save(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.toDO(order);
        int result = orderMapper.insert(orderDO);
        return result > 0;
    }

    @Override
    public Order getById(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        if (orderDO != null) {
            return OrderDOConvertor.INSTANCE.toDomain(orderDO);
        }
        return null;
    }

    @Override
    public boolean updateById(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.toDO(order);
        int result = orderMapper.updateById(orderDO);
        return result > 0;
    }
}

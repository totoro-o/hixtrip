package com.hixtrip.sample.infra.db;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.generator.OrderIdGenerator;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    private final OrderIdGenerator orderIdGenerator;

    /**
     * 创建订单
     *
     * @param order
     */
    @Override
    public void createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.fromDomain(order);
        String id = orderIdGenerator.build();
        orderDO.setId(id);
        orderMapper.insert(orderDO);
        order.setId(orderDO.getId());
    }

    /**
     * getByOrderId
     *
     * @param orderId
     * @return
     */
    @Override
    public Order getByOrderId(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        if (orderDO == null) {
            throw new RuntimeException("订单不存在，请重试。");
        }
        return OrderDOConvertor.INSTANCE.toDomain(orderDO);
    }

    /**
     * update by id
     *
     * @param order
     */
    @Override
    public void updateById(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.fromDomain(order);
        orderMapper.updateById(orderDO);
    }


}

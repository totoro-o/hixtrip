package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.orderDomainToDO(order);
        orderMapper.insert(orderDO);
    }

    /**
     * 更新订单
     * @param commandPay
     * @param payStatusEnum
     */
    @Override
    public void updateOrder(CommandPay commandPay, PayStatusEnum payStatusEnum) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        if (orderDO == null) {
            throw new RuntimeException("订单不存在！");
        }
        orderDO.setPayStatus(payStatusEnum.getPayStatus());
        orderDO.setPayTime(LocalDateTime.now());
        orderMapper.update(orderDO, new UpdateWrapper<OrderDO>().eq("id", orderDO.getId()));
    }

    /**
     * 根据ID获取订单
     * @param orderId 订单ID
     * @return Order
     */
    @Override
    public Order getOrderById(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        return OrderDOConvertor.INSTANCE.orderDOToDomain(orderDO);
    }

}

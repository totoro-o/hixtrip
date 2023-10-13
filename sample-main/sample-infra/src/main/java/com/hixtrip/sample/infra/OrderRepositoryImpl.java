package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.constants.OrderConstants;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单操作
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 保存订单信息
     *
     * @param order
     */
    @Override
    public void saveOrderInfo(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
        orderDO.setId(UUID.randomUUID().toString().replaceAll("-",""));
        orderDO.setCreateTime(LocalDateTime.now());
        orderDO.setDelFlag(0L);
        // 支付状态设置未支付
        orderDO.setPayStatus(OrderConstants.PAY_STATUS_NOPAY);
        orderMapper.insert(orderDO);
    }

    /**
     * 更新订单支付信息
     * @param orderId
     * @param payStatus
     */
    @Override
    public void updateOrderPayStatus(String orderId, String payStatus) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        orderDO.setPayStatus(payStatus);
        orderDO.setPayTime(LocalDateTime.now());
        orderDO.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(orderDO);
    }
}

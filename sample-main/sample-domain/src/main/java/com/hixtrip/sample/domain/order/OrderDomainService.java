package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.constants.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        order.setPayStatus(PayStatusEnum.WAIT.getValue());
        orderRepository.saveOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public Order orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getById(commandPay.getOrderId());
        if (order != null) {
            order.setPayStatus(PayStatusEnum.SUCCESS.getValue());
            orderRepository.updateOrder(order);
        }
        return order;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public Order orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getById(commandPay.getOrderId());
        if (order != null) {
            order.setPayStatus(PayStatusEnum.FAIL.getValue());
            orderRepository.updateOrder(order);
        }
        return order;
    }
}

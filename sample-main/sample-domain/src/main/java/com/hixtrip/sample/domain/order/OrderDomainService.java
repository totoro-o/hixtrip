package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@AllArgsConstructor
@Component
public class OrderDomainService {

    private final OrderRepository orderRepository;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        order.createOrder();
        orderRepository.createOrder(order);
        return order;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public Order orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getByOrderId(commandPay.getOrderId());
        order.paySuccess();
        orderRepository.updateById(order);
        return order;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public Order orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getByOrderId(commandPay.getOrderId());
        order.payFail();
        orderRepository.updateById(order);
        return order;
    }
}

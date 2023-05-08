package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    OrderRepository orderRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        return orderRepository.createOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(Order order) {
        //需要你在infra实现, 自行定义出入参
        order.setPayStatus(Order.PayStatusEnum.PAY_SUCCESS.getCode());
        orderRepository.update(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(Order order) {
        //需要你在infra实现, 自行定义出入参
        order.setPayStatus(Order.PayStatusEnum.PAY_FAIL.getCode());
        orderRepository.update(order);
    }

    public void orderDuplicatePay(Order order) {

    }
}

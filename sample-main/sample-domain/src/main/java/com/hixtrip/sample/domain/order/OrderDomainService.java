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
    private OrderRepository orderRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public String createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参


        String orderId = orderRepository.createOrder(order);
        return orderId;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public Order orderPaySuccess(Order order) {
        //需要你在infra实现, 自行定义出入参
        String orderTailNumber = order.getId().substring(order.getId().length() - 4);// 截取尾号为最后四位
        order.setOrderTailNumber(orderTailNumber);
        // 更新订单状态
        orderRepository.updateOrder(order);

        return order;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public Order orderPayFail(Order order) {
        //需要你在infra实现, 自行定义出入参
        String orderTailNumber = order.getId().substring(order.getId().length() - 4);// 截取尾号为最后四位
        order.setOrderTailNumber(orderTailNumber);
        // 更新订单状态
        orderRepository.updateOrder(order);
        return order;
    }

}

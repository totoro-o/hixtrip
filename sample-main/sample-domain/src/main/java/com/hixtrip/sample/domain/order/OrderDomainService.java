package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.client.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    public Boolean orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        //订单校验防止多次回调（也可以用缓存处理幂等）
        Order order = orderRepository.getOrderById(commandPay.getOrderId());
        if (PayStatusEnum.getPayStatusEnum(order.getPayStatus()) == PayStatusEnum.PAID) {
            //重复调用
            return false;
        }
        //修改订单支付情况
        order.setPayStatus(PayStatusEnum.PAID.getCode());
        order.setPayTime(LocalDateTime.now());
        order.setUpdateBy("callback");
        order.setUpdateTime(LocalDateTime.now());
        orderRepository.updateOrder(order);
        return true;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public Boolean orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getOrderById(commandPay.getOrderId());
        if (PayStatusEnum.getPayStatusEnum(order.getPayStatus()) == PayStatusEnum.UN_PAID) {
            //重复调用
            return false;
        }
        //修改订单支付情况
        order.setPayStatus(PayStatusEnum.UN_PAID.getCode());
        order.setPayTime(LocalDateTime.now());
        order.setUpdateBy("callback");
        order.setUpdateTime(LocalDateTime.now());
        orderRepository.updateOrder(order);
        return true;
    }

    /**
     * 根据orderId 获取订单
     * @param orderId
     * @return
     */
    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }
}

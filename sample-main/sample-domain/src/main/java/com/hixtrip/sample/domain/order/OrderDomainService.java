package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 创建待付款订单
     */
    public void createOrder(Order order) throws NoSuchAlgorithmException, IllegalAccessException {
        //需要你在infra实现, 自行定义出入参
        orderRepository.createOrder(order);
    }

    /**
     * 待付款订单支付成功
     */
    public String orderPaySuccess(CommandPay commandPay) {
        return orderRepository.orderPaySuccess(commandPay);
    }

    /**
     * 待付款订单支付失败
     */
    public String orderPayFail(CommandPay commandPay) {
       return orderRepository.orderPayFail(commandPay);
    }
}

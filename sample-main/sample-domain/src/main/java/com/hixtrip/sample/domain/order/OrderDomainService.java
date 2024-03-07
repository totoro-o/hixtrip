package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    public Order createOrder(String userId, String skuId, Integer amount, BigDecimal skuPrice) {
        //需要你在infra实现, 自行定义出入参
        Order order = new Order();
        order.setUserId(userId);
        order.setSkuId(skuId);
        order.setAmount(amount);
        order.setMoney(skuPrice.multiply(BigDecimal.valueOf(amount)));
        order.setPayStatus("wait");
        order.setPayTime(LocalDateTime.now());
        this.orderRepository.createOrder(order);
        return order;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        Order order = new Order();
        order.setOrderId(commandPay.getOrderId());
        order.paySuccess();
        this.orderRepository.updateOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        Order order = new Order();
        order.setOrderId(commandPay.getOrderId());
        order.payFail();
        this.orderRepository.updateOrder(order);
    }
}

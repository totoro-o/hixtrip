package com.hixtrip.sample.domain.order.listener;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.enums.PayStatus;
import com.hixtrip.sample.domain.pay.event.PaidEvent;
import com.hixtrip.sample.domain.pay.event.PayFailedEvent;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:29
 * 支付域事件监听处理器
 */
@Component
public class OrderPaidEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDomainService orderDomainService;

    @org.springframework.core.annotation.Order(1)
    @EventListener(value = PaidEvent.class)
    public void payCallback(PaidEvent event) {
        CommandPay commandPay = event.getSource().getCommandPay();
        Optional<Order> optional = orderRepository.getById(commandPay.getOrderId());
        if (optional.isEmpty()) {
            return;
        }
        Order order = optional.get();
        if(PayStatus.UNPAID.name().equals(order.getPayStatus())) {
            orderDomainService.orderPaySuccess(commandPay);
        }
    }

    @org.springframework.core.annotation.Order(1)
    @EventListener(value = PayFailedEvent.class)
    public void payCallback(PayFailedEvent event) {
        CommandPay commandPay = event.getSource().getCommandPay();
        Optional<Order> optional = orderRepository.getById(commandPay.getOrderId());
        if (optional.isEmpty()) {
            return;
        }
        Order order = optional.get();
        if(PayStatus.UNPAID.name().equals(order.getPayStatus())) {
            orderDomainService.orderPayFail(commandPay);
        }
    }
}

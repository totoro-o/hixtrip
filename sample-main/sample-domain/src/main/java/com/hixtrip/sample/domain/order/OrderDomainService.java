package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.saveOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getById(commandPay.getOrderId()).get();
        order.updatePaid();

        transactionTemplate.executeWithoutResult(e -> {
            orderRepository.updateOrder(order);
            inventoryDomainService.changeInventory(order.getSkuId(), null, null, Long.valueOf(order.getAmount()));
        });
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参

        Order order = orderRepository.getById(commandPay.getOrderId()).get();
        order.updatePayFailed();

        orderRepository.updateOrder(order);
        // 订单支付失败，非订单完结节点，不需要返还库存
    }
}

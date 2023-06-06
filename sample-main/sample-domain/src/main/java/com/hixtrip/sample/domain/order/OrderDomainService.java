package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        //保存订单
        orderRepository.createOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(String orderNumber) {

        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 更新订单状态为已支付
        order.setOrderStatus("已支付");

        // 保存订单
        orderRepository.updateOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(String orderNumber) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        boolean inventoryRolledBack = inventoryRepository.rollbackInventory(order.getSkuId(), order.getSkuNum());
        if (!inventoryRolledBack) {
            throw new RuntimeException("库存回滚失败");
        }
        // 更新订单状态为已支付
        order.setOrderStatus("支付失败");
        // 保存订单
        orderRepository.updateOrder(order);
        //回滚库存


    }
}

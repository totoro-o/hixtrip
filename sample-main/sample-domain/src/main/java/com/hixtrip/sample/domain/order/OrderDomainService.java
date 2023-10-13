package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.session.model.SessionInfo;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private SessionRepository sessionRepository;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        // 判断订单商品库存是否能够扣除
        Integer sellableInventory = inventoryDomainService.getInventory(order.getSkuId());
        if (sellableInventory <= 0) {
            throw new RuntimeException("创建订单失败，库存不足！");
        }
        // 创建订单,扣除预占库存
        // 保存订单信息
        SessionInfo sessionInfo = sessionRepository.getSessionInfo();
        order.setUserId(sessionInfo.getUser_id());
        order.setCreateBy(sessionInfo.getUser_name());
        orderRepository.saveOrderInfo(order);
        // 扣除预占库存
        Boolean flag = inventoryDomainService.changeInventory(order.getSkuId(), sellableInventory.longValue(), order.getAmount().longValue(), 0L);
        if(!flag){
            throw new RuntimeException("扣除库存失败！");
        }
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.updateOrderPayStatus(commandPay.getOrderId(), "1");
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.updateOrderPayStatus(commandPay.getOrderId(), "2");
    }
}

package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单领域服务
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private CommodityDomainService commodityDomainService;


    /**
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        // 需要你在infra实现, 自行定义出入参
        // 获取可售库存数量
        Integer sellableInventory = inventoryDomainService.getInventory(order.getSkuId());
        if (sellableInventory < order.getAmount()) {
            throw new RuntimeException("库存不足！订单创建失败！");
        }
        // 获取商品价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());
        order = order.toBuilder()
                .id(UUID.randomUUID().toString())
                .money(skuPrice.multiply(new BigDecimal(order.getAmount())))
                .payStatus(PayStatusEnum.UN_PAY.getPayStatus())
                .delFlag(0L)
                .createTime(LocalDateTime.now()).build();
        // 创建订单
        orderRepository.createOrder(order);
        // 修改库存
        Long amount = Long.valueOf(order.getAmount());
        Boolean changeResult = inventoryDomainService.changeInventory(order.getSkuId(),
                Math.negateExact(amount), // 扣除可售库存
                amount, // 增加预占库存
                0L);// 订单尚未支付，实际占用库存不变
        if (!changeResult) {
            throw new RuntimeException("库存不足！");
        }
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        // 更新订单
        orderRepository.updateOrder(commandPay, PayStatusEnum.PAY_SUCCESS);
        // 获取订单
        Order order = orderRepository.getOrderById(commandPay.getOrderId());
        // 修改库存
        Long amount = Long.valueOf(order.getAmount());
        Boolean changeResult = inventoryDomainService.changeInventory(order.getId(),
                0L, // 可售库存不变 (可售库存在创建订单时已扣除)
                Math.negateExact(amount), // 扣除预占库存 (订单支付成功，将预占库存转为实际占用库存)
                amount); // 增加实际占用库存
        if (!changeResult) {
            throw new RuntimeException("库存不足！");
        }
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        // 更新订单
        orderRepository.updateOrder(commandPay, PayStatusEnum.PAY_FAILURE);
        // 获取订单
        Order order = orderRepository.getOrderById(commandPay.getOrderId());
        // 修改库存
        Long amount = Long.valueOf(order.getAmount());
        Boolean changeResult = inventoryDomainService.changeInventory(order.getId(),
                amount, // 增加可售库存
                Math.negateExact(amount), // 扣除预占库存 (订单支付失败，将预占库存加回到可售库存)
                0L); // 订单支付失败，实际占用库存不变
        if (!changeResult) {
            throw new RuntimeException("库存不足！");
        }
    }

    /**
     * 订单重复支付
     *
     * @param commandPay
     */
    public void orderPayDuplicate(CommandPay commandPay) {
        // 重复支付...
    }
}

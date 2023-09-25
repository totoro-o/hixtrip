package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.strategy.PayStatus;
import com.hixtrip.sample.domain.pay.strategy.DuplicatePaymentStrategy;
import com.hixtrip.sample.domain.pay.strategy.PayCallStrategyContext;
import com.hixtrip.sample.domain.pay.strategy.PaymentFailureStrategy;
import com.hixtrip.sample.domain.pay.strategy.PaymentSuccessStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Transactional
    @Override
    public String order(CommandOderCreateDTO commandOderCreateDTO) {
        String skuId = commandOderCreateDTO.getSkuId();

        //查询sku价格 (模拟，不需要实现)
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);

        //扣减库存 (只在缓存实现)
        //首先检查可售库存是否大于等于购买数量
        Inventory inventory = inventoryDomainService.getInventory(skuId);
        if (inventory == null || inventory.getSellableQuantity() < commandOderCreateDTO.getAmount()) {
            //抛出可售库存不足错误
        }
        //将购买数量从可售库存中减去，并将减去的数量记录到预占库存中。
        inventory.setSellableQuantity(inventory.getSellableQuantity() - commandOderCreateDTO.getAmount());
        inventory.setWithholdingQuantity(inventory.getWithholdingQuantity() + commandOderCreateDTO.getAmount());
        inventoryDomainService.changeInventory(inventory.getSkuId(), inventory.getSellableQuantity(), inventory.getWithholdingQuantity(), inventory.getOccupiedQuantity());

        //生成订单
        Order order = new Order();
        order.setSkuId(skuId);
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setMoney(new BigDecimal(order.getAmount() * skuPrice.longValue()));
        order.setUserId(commandOderCreateDTO.getUserId());
        order = orderDomainService.createOrder(order);

        return order.getId();
    }

    @Transactional
    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        PayCallStrategyContext paymentContext;
        String orderId = commandPayDTO.getOrderId();

        if (commandPayDTO.getPayStatus().equals(PayStatus.SUCCESS)) {
            paymentContext = new PayCallStrategyContext(new PaymentSuccessStrategy());
            paymentContext.execute(orderId);

        } else if (commandPayDTO.getPayStatus().equals(PayStatus.FAIL)) {
            paymentContext = new PayCallStrategyContext(new PaymentFailureStrategy());
            paymentContext.execute(orderId);

        } else if (commandPayDTO.getPayStatus().equals(PayStatus.DUPLICATE)) {
            paymentContext = new PayCallStrategyContext(new DuplicatePaymentStrategy());
            paymentContext.execute(orderId);

        } else {
            return String.format("操作失败，存在其他回调状态：%s", commandPayDTO.getPayStatus());
        }

        return "操作成功";
    }
}

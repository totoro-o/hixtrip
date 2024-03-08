package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
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

        //库存扣减
        Boolean changeInventoryStatus = inventoryDomainService.changeInventory(order.getSkuId(),
                null,order.getAmount().longValue(),null);

        if (!changeInventoryStatus) {
            throw new IllegalStateException("库存不足，创建订单失败");
        }

        //获取sku价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());

        //更新订单信息
        order.createNewOrder(skuPrice);

        orderRepository.save(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        Order order = orderRepository.getByOrderId(commandPay.getOrderId());
        if (order == null) {
            throw new IllegalStateException("订单不存在");
        }

        //库存更新
        Boolean changeInventoryStatus = inventoryDomainService.changeInventory(order.getSkuId(),
                null,null,order.getAmount().longValue());

        if (!changeInventoryStatus) {
            throw new IllegalStateException("库存不足，支付失败");
        }

        //更新订单状态
        order.updateOrderStatus(commandPay.getPayStatus());

        orderRepository.updateOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        Order order = orderRepository.getByOrderId(commandPay.getOrderId());
        if (order == null) {
            throw new IllegalStateException("订单不存在");
        }

        //库存更新
        Boolean changeInventoryStatus = inventoryDomainService.changeInventory(order.getSkuId(),
                order.getAmount().longValue(),null,null);

        //更新订单状态
        order.updateOrderStatus(commandPay.getPayStatus());

        orderRepository.updateOrder(order);
    }
}

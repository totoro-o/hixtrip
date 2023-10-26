package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.constants.OrderPayCallbackStatusEnum;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.CommandOrderCreate;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
@RequiredArgsConstructor
public class OrderDomainService {

    private final CommodityDomainService commodityDomainService;
    private final OrderRepository orderRepository;
    private final InventoryDomainService inventoryDomainService;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(CommandOrderCreate commandOrderCreate) {
        // 预占库存
        String skuId = commandOrderCreate.getSkuId();
        Boolean changeResult = inventoryDomainService.changeInventory(skuId, 0, 1, 0);
        Assert.isTrue(changeResult, "库存不足");

        try {
            BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
            String id = UUID.randomUUID().toString();
            Order order = Order.init(id, commandOrderCreate.getUserId(), skuId, commandOrderCreate.getAmount(), skuPrice);
            boolean saveResult = orderRepository.save(order);
            Assert.isTrue(saveResult, "保存订单失败");
            return order;
        } catch (Exception e) {
            // 创建订单失败，回滚预占
            changeResult = inventoryDomainService.changeInventory(skuId, 0, -1, 0);
            Assert.isTrue(changeResult, "库存回滚失败");
            throw e;
        }
    }

    /**
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        OrderPayCallbackStatusEnum statusEnum = OrderPayCallbackStatusEnum.getByCode(commandPay.getPayStatus());
        boolean isPaySuccess = OrderPayCallbackStatusEnum.SUCCESS.equals(statusEnum);
        Assert.isTrue(isPaySuccess, "支付状态不合法");

        Order order = orderRepository.getById(commandPay.getOrderId());
        Assert.notNull(order, "订单不存在");
        order.paySuccess();

        boolean result = orderRepository.updateById(order);
        Assert.isTrue(result, "更新订单失败");

        // 支付成功，转移预占库存至真实库存
        Boolean changeResult = inventoryDomainService.changeInventory(order.getSkuId(), 0, -1, 1);
        Assert.isTrue(changeResult, "库存更新失败");
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        OrderPayCallbackStatusEnum statusEnum = OrderPayCallbackStatusEnum.getByCode(commandPay.getPayStatus());
        boolean isPayFail = OrderPayCallbackStatusEnum.FAILURE.equals(statusEnum);
        Assert.isTrue(isPayFail, "支付状态不合法");

        Order order = orderRepository.getById(commandPay.getOrderId());
        Assert.notNull(order, "订单不存在");

        order.payFail();

        boolean result = orderRepository.updateById(order);
        Assert.isTrue(result, "更新订单失败");

        // 支付失败，回滚预占库存
        Boolean changeResult = inventoryDomainService.changeInventory(order.getSkuId(), 0, -1, 0);
        Assert.isTrue(changeResult, "库存回滚失败");
    }
}

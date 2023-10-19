package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.common.IdWorker;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 订单领域服务
 */
@Component
@RequiredArgsConstructor
public class OrderDomainService {

    private final InventoryDomainService inventoryDomainService;
    private final CommodityDomainService commodityDomainService;
    private final OrderRepository repository;
    private final IdWorker idWorker;

    /**
     * 创建待付款订单,
     * @return 订单创建成功返回订单，返回null表示库存不足
     */
    public @Nullable Order createOrder(@NotNull Order order) {
        // 初始化订单
        order.initOrder(idWorker.getLongId("order:" + LocalDateTime.now().getHour()));
        // 查询商品价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());
        if (Objects.isNull(skuPrice)) {
            // 错误的商品ID
            return null;
        }
        // 填充订单价格
        order.initOrderMoney(skuPrice);
        // 预扣订单
        Boolean changeRes = inventoryDomainService.changeInventory(
                order.getSkuId(),
                null,
                order.getAmount(),
                null);
        if (!changeRes) {
            // 预占库存失败
            return null;
        }
        // 预占库存成功，插入订单
        repository.insert(order);
        return order;
    }

    /**
     * 待付款订单支付成功
     * 支付成功后还有后续的确认收货的步骤，待订单确认收货完成后订单最终才进入finished，题目中未要求实现此功能，故忽略这部分的逻辑
     */
    public void orderPaySuccess(@NotNull CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = repository.findById(commandPay.getOrderId());
        order.orderPay();
        // 商品预占库存转移
        Boolean res = inventoryDomainService.changeInventory(order.getSkuId(), null, null, order.getAmount());
        if (res) {
            repository.update(order);
            return;
        }
        // 处理订单退款，库存转移失败
        System.out.println("模拟发起订单退款");
        // 退款需要取消预占用的库存
        inventoryDomainService.changeInventory(order.getSkuId(), null, -order.getAmount(), null);
    }

    /**
     * 待付款订单支付失败
     */
    public void orderPayFail(@NotNull CommandPay commandPay) {
        Order order = repository.findById(commandPay.getOrderId());
        order.closeOrder();
        // 归还预占用的库存
        inventoryDomainService.changeInventory(order.getSkuId(), null, -order.getAmount(), null);
        repository.update(order);
    }
}

package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private PayDomainService payDomainService;


    @Autowired
    private OrderRepository orderRepository;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        //购买金额的计算
        BigDecimal amount = new BigDecimal(order.getAmount());
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());
        BigDecimal money = skuPrice.multiply(amount);
        order.setMoney(money);
        //判断商品库存是否满足订单的要求
        BigInteger inventory = inventoryDomainService.getInventory(order.getSkuId());
        if (inventory.compareTo(amount.toBigInteger()) < 0) {
            throw new RuntimeException("订单创建失败");
        }
        return orderRepository.createOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        if ("待付款".equals(commandPay.getPayStatus())) {
            commandPay.setPayStatus("付款失败");
            //通过读锁获取订单，避免重复支付
            Map<Object, Object> order = orderRepository.getOrder(commandPay.getOrderId());
            Object skuId = order.get("skuId");
            Object amountCache = order.get("amount");
            if (Objects.nonNull(skuId) && Objects.nonNull(amountCache)) {
                BigInteger inventory = inventoryDomainService.getInventory(skuId.toString());
                BigInteger amount = new BigInteger(amountCache.toString());
                if (inventory.compareTo(amount) >= 0) {
                    //先进行预占库存扣减，避免超卖
                    inventoryDomainService.changeInventory(skuId.toString(), inventory.longValue(), amount.longValue(), null);
                    Boolean isPar = payDomainService.payRecord(commandPay);
                    if (isPar) {
                        //再进行占用库存更新
                        inventoryDomainService.changeInventory(skuId.toString(), inventory.longValue(), null, amount.longValue());
                        //删除订单
                        orderRepository.delOrderById(commandPay.getOrderId());
                        commandPay.setPayStatus("已付款");
                    }
                }
            }
        }
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        //支付失败后，进行一次重新支付，如果成功则改变支付状态，失败还是支付失败
        if ("付款失败".equals(commandPay.getPayStatus())) {
            commandPay.setPayStatus("待付款");
            orderPaySuccess(commandPay);
        }
    }
}

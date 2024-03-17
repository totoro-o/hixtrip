package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreatedVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayCallbackContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@AllArgsConstructor
@Component
public class OrderServiceImpl implements OrderService {

    private final OrderDomainService orderDomainService;

    private final InventoryDomainService inventoryDomainService;

    private final CommodityDomainService commodityDomainService;

    private final PayCallbackContext payCallbackContext;


    @Override
    public OrderCreatedVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {

        // 验证参数
        Assert.notNull(commandOderCreateDTO.getSkuId(), "skuId不能为空");
        Assert.isTrue(commandOderCreateDTO.getAmount() > 0, "商品数量不能少于0");
        Assert.notNull(commandOderCreateDTO.getUserId(), "用户id不能为空");

        // 验证库存
        Integer inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        Assert.isTrue(inventory != null && inventory > 0, "库存不足");

        // 预占库存
        inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), 0L, (long) commandOderCreateDTO.getAmount(), 0L);

        // 获取商品信息
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        Assert.notNull(skuPrice, "商品不存在");

        // 创建订单
        Order order = OrderConvertor.INSTANCE.convertToDomain(commandOderCreateDTO);
        order.setMoney(skuPrice);
        orderDomainService.createOrder(order);

        return OrderConvertor.INSTANCE.convertToVO(order);
    }

    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.toCommandPay(commandPayDTO);
        return payCallbackContext.handle(commandPay);
    }
}

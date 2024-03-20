package com.hixtrip.sample.app.service;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.client.order.vo.PayCallBackVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.chain.PayHandlerChainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayStrategySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;


    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private PayHandlerChainService payHandlerChainService;

    @Autowired
    private PayStrategySelector payStrategySelector;


    @Override
    public OrderVO order(CommandOderCreateDTO commandOderCreateDTO) {

        Assert.notNull(commandOderCreateDTO.getAmount(),"商品数量不能为空");
        Assert.notNull(commandOderCreateDTO.getSkuId(),"商品id不能为空");
        Assert.notNull(commandOderCreateDTO.getUserId(),"用户id不能为空");


        //校验库存
        Inventory inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        Assert.isTrue(Objects.nonNull(inventory.getSellableQuantity()) && inventory.getSellableQuantity() > 0,"库存不足");

        //获取商品信息
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        Assert.notNull(skuPrice,"未找到商品");

        //占库存
        inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(),-Long.valueOf(commandOderCreateDTO.getAmount()),
                Long.valueOf(commandOderCreateDTO.getAmount()),0L);

        //下单
        Order order = OrderConvertor.INSTANCE.commandOderCreateDTOToOrder(commandOderCreateDTO);
        order.setMoney(skuPrice.multiply(BigDecimal.valueOf(order.getAmount())));
        return OrderConvertor.INSTANCE.orderToOrderVO(orderDomainService.createOrder(order));
    }

    @Override
    public PayCallBackVO payCallback(CommandPayDTO commandPayDTO) {

        CommandPay commandPay = PayConvertor.INSTANCE.commandPayDTOToCommandPay(commandPayDTO);

        //处理订单库存
        // payHandlerChainService.getHandlerChain().doHandler(commandPay);//责任链模式
        payStrategySelector.getStrategy(commandPayDTO.getPayStatus()).handler(commandPay);//策略模式

        return PayConvertor.INSTANCE.commandPayToPayCallBackVO(commandPay);
    }

}

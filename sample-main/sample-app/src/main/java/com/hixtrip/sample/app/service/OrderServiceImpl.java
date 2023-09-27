package com.hixtrip.sample.app.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayCallBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @Autowired
    private PayCallBackService payCallBackService;

    @Override
    public OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        String skuId = commandOderCreateDTO.getSkuId();
        //扣减库存
        Integer amount = commandOderCreateDTO.getAmount();
        Boolean changeFlag = inventoryDomainService.changeInventory(skuId, 0L, Long.parseLong(String.valueOf(amount)), 0L);
        if (!changeFlag) {
            return OrderVO.builder().build();
        }
        //查询价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
        BigDecimal money = skuPrice.multiply(new BigDecimal(amount));
        //创建订单
        Order order = Order.builder()
                .id(IdWorker.getIdStr())
                .userId(commandOderCreateDTO.getUserId())
                .skuId(skuId)
                .amount(amount)
                .money(money)
                .createBy(commandOderCreateDTO.getUserId())
                .createTime(LocalDateTime.now())
                .build();
        orderDomainService.createOrder(order);
        return OrderVO.builder().build();
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        payCallBackService.payCallBack(CommandPay.builder()
                .orderId(commandPayDTO.getOrderId())
                .payStatus(commandPayDTO.getPayStatus())
                .build());
    }
}

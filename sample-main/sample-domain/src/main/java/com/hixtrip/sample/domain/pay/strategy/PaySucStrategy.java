package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaySucStrategy implements PayStrategy{

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void handler(CommandPay commandPay) {
        //调用支付
        payDomainService.payRecord(commandPay);
        //修改订单状态
        Order order = orderDomainService.orderPaySuccess(commandPay);
        //修改库存
        inventoryDomainService.changeInventory(order.getSkuId(),0L, - Long.valueOf(order.getAmount()), Long.valueOf(order.getAmount()));

    }
}

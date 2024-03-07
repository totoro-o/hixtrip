package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.constants.PayStatusConstant;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Component(value = PayStatusConstant.SUCCESS)
public class PaySuccessEvent implements PayEvent{

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void payCallback(CommandPay commandPay) {
        Order order = orderDomainService.orderPaySuccess(commandPay);
        //支付成功 占用库存
        Inventory inventory = inventoryDomainService.getInventory(order.getSkuId());
        inventory.occupied(order.getAmount());
        inventoryDomainService.changeInventory(inventory);
    }
}

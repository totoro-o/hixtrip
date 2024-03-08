package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentCallback;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 失败支付
 * @创建人 liyinglong
 * @创建时间 2024/3/7
 * @描述
 */
public class FailurePaymentCallback implements PaymentCallback {
    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void handlePaymentResult(Order order, String payStatus) {
        //更新订单状态
        order.setPayStatus(payStatus);
        orderDomainService.orderPayFail(order);
        // 更新库存
        inventoryDomainService.changeInventory(order.getSkuId(), (long) order.getAmount(), (long) 0 - order.getAmount(), null, order.getAmount());
    }

}

package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 支付成功策略
 */
@AllArgsConstructor
@Component
public class PaySuccessStrategy extends AbstractPayCallbackStrategy implements PayCallbackStrategy {

    private final OrderDomainService orderDomainService;

    private final InventoryDomainService inventoryDomainService;

    @Override
    public PayStatusEnum getPayStatus() {
        return PayStatusEnum.SUCCESS;
    }

    @Override
    public void doHandle(CommandPay commandPay) {
        Order order = orderDomainService.orderPaySuccess(commandPay);
        inventoryDomainService.changeInventory(order.getSkuId(), 0L, (long) -order.getAmount(), (long) order.getAmount());
    }

}

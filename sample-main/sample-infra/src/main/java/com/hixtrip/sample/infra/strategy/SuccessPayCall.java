package com.hixtrip.sample.infra.strategy;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.strategy.IPayCall;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.order.constant.OrderStatusConstant;
import com.hixtrip.sample.domain.pay.constant.PayStatusConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SuccessPayCall implements IPayCall {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void payCall(CommandPay commandPay) {
        // 将预扣库存转换成实扣库存
        Order order = orderRepository.getOrderByOrderId(commandPay.getOrderId());
        String skuId = order.getSkuId();
        Integer amount = order.getAmount();
        inventoryRepository.changeInventory(skuId, null, Long.valueOf(amount),-1 * Long.valueOf(amount));
        // 修改订单支付状态
        orderRepository.orderToSuccess(commandPay.getOrderId(), commandPay.getPayId(),
                OrderStatusConstant.ORDER_PURCHASE,PayStatusConstant.PAY_SUCCESS);
    }

    @Override
    public String payStatus() {
        return PayStatusConstant.PAY_SUCCESS;
    }
}

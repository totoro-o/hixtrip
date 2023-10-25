package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.command.CreateOrderCommand;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        // 分布式锁 避免重复创建 try{下面所有}  finally 略
        //

        // 处理库存
        try {
            // 记录流水, 略
            Boolean changeResult = inventoryDomainService.changeInventory(command.getSkuId(), (long) -command.getAmount(), (long) command.getAmount(), 0L);
            Assert.isTrue(changeResult, "库存扣减失败");
        } catch (Exception e) {
            // 回滚流水
        }

        try {
            // 记录订单, 写入相关信息
            Order order = new Order();
            order = orderDomainService.createOrder(order);
            return order;
        } catch (Exception e) {
            // 异步消息 回滚流水 回滚库存
            throw e;
        }
    }
}

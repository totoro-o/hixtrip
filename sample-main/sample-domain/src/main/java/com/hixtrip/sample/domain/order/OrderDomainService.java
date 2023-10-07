package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private OrderRepository orderRepository;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {

        // 校验库存
        if (checkInventory(order)) {
            // 模拟购买金额,从商品查出
            BigDecimal money = new BigDecimal("1");
            // 创建待付款订单
            order.createOrder(money);
            // 保存订单
            if(orderRepository.save(order) > 0){
                // todo 修改三个库存的业务逻辑不清楚
                inventoryDomainService.changeInventory(order.getSkuId(),0L,0L,0L);
            };
        }
        throw new RuntimeException("[库存不足]");
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
    }


    /**
     * ==== 以下提取方法,可移至其他类 ====
     */

    /**
     * 库存校验
     */
    private boolean checkInventory(Order order) {
        Integer inventory = inventoryDomainService.getInventory(order.getSkuId());
        return (inventory - order.getAmount()) >= 0;
    }

}

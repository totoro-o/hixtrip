package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        //需要你在infra实现, 自行定义出入参
        // 判断sku库存是否充足
        Integer skuInventory = inventoryDomainService.getInventory(order.getSkuId());
        if (skuInventory <= 0 && skuInventory < order.getAmount()) {
            throw new RuntimeException("库存不足");
        }
        // 创建订单,扣除预占库存
        // 保存订单信息
        String lockKey = "CREATE-ORDER-LOCK:" + order.getUserId() + ":" + order.getSkuId();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", Duration.ofSeconds(10));
        if (lock) {
            OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDo(order);
            orderDO.setId(UUID.randomUUID().toString());
            orderDO.setPayStatus(PayStatusEnum.NO_PAY.getCode());
            try {
                // 锁定库存，刚开始创建订单的时候设定为预占库存
                // 预占库存
                Boolean flag = inventoryDomainService.changeInventory(order.getSkuId(), null, order.getAmount().longValue(), null);
                if (!flag) {
                    throw new RuntimeException("预占库存失败");
                }
                // 创建订单
                orderRepository.createOrder(orderDO);
            } finally {
                redisTemplate.delete(lockKey);
            }
        }
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        OrderDO order = orderRepository.queryById(commandPay.getOrderId());
        if (Objects.isNull(order)) {
            throw new RuntimeException("订单不存在");
        }
        order.setPayStatus(commandPay.getPayStatus());
        order.setPayTime(LocalDateTime.now());
        //更新入库
        orderRepository.updateOrderById(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        //需要你在infra实现, 自行定义出入参
        OrderDO order = orderRepository.queryById(commandPay.getOrderId());
        if(Objects.isNull(order)){
            throw new RuntimeException("订单不存在");
        }
        order.setPayStatus(commandPay.getPayStatus());
        orderRepository.updateOrderById(order);
        inventoryDomainService.changeInventory(order.getSkuId(),null,-order.getAmount().longValue(),null);
    }
}

package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.exception.AppException;
import com.hixtrip.sample.app.utils.RedisLockUtil;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.dto.OrderDomainCreateCmd;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Slf4j
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private RedisLockUtil redisLockUtil;

    @Override
    public void saveOrder(CommandOderCreateDTO cmd) {

        // 设定所有的skuId都是存在，此处不判定商品域是否异常
        BigDecimal balance = commodityDomainService.getSkuPrice(cmd.getSkuId());

        int sellableQuantity = 0;
        try {
            sellableQuantity = inventoryDomainService.getInventory(cmd.getSkuId());
        } catch (Exception e){
            throw new AppException(e.getMessage());
        }

        // 判定库存是否充足
        if (cmd.getAmount().compareTo(sellableQuantity) > 0) {
            throw new AppException("库存不足");
        }

        // 获取锁
        // 使用用户ID+skuId作为key
        String key = "order:create:" + cmd.getUserId() +":" + cmd.getSkuId();
        String value = UUID.randomUUID().toString();
        boolean getLock = redisLockUtil.getLock(key, value);
        if(getLock) {
            // 获取锁成功，业务开始
            OrderDomainCreateCmd domainCreateCmd = OrderDomainCreateCmd.builder()
                    .skuId(cmd.getSkuId())
                    .userId(cmd.getUserId())
                    .amount(cmd.getAmount())
                    .money(balance.multiply(new BigDecimal(cmd.getAmount())))
                    .build();
            Order order = Order.createOrder(domainCreateCmd);

            // 事物锁
            transactionTemplate.executeWithoutResult(s -> {
                try {
                    // 锁定库存，刚开始创建订单的时候设定为预占库存
                    inventoryDomainService.changeInventory(cmd.getSkuId(), null, Long.valueOf(cmd.getAmount()), null);
                    // 创建订单
                    orderDomainService.createOrder(order);
                }catch (Exception e) {
                    // 出现异常需要回滚，库存
                    inventoryDomainService.changeInventory(cmd.getSkuId(), null, -Long.valueOf(cmd.getAmount()), null);
                }finally {
                    redisLockUtil.removeLock(key, value);
                }
            });
            redisLockUtil.removeLock(key, value);
        } else {
            // 正在处理之前的业务
        }

    }

    @Override
    public void payCallback(CommandPayDTO cmd) {

        Optional<Order> optional = orderRepository.getById(cmd.getOrderId());
        if (optional.isEmpty()) {
            // 没有查询到订单，直接返回
            return;
        }

        // 通过orderId请求支付记录
        CommandPay commandPay = CommandPay.builder()
                .orderId(cmd.getOrderId())
                .payStatus(cmd.getPayStatus())
                .build();
        payDomainService.payRecord(commandPay);
    }
}

package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.paystrategy.*;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
    private PayDomainService payDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Override
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        Order order = new Order();
        order.create();
        order.setSkuId(commandOderCreateDTO.getSkuId());
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setUserId(commandOderCreateDTO.getUserId());

        //查询sku价格
        var skuPrice = commodityDomainService.getSkuPrice(order.getSkuId());
        var userId= commandOderCreateDTO.getUserId();
        var money = skuPrice.multiply(new BigDecimal(order.getAmount()));
        order.setMoney(money);

        //扣减库存
        var inventory = inventoryDomainService.getInventory(order.getSkuId());
        inventoryDomainService.changeInventory(order.getSkuId(),inventory.longValue(),order.getAmount().longValue(),order.getAmount().longValue());

        //支付
        CommandPay commandPay = new CommandPay();
        commandPay.setOrderId(order.getId());
        commandPay.setPayStatus(order.getPayStatus());
        payDomainService.payRecord(commandPay);
        order.paying();
        //创建订单
        orderDomainService.createOrder(order);
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        //根据订单状态执行不同的策略实现
        PaymentStrategy paymentStrategy = null;
        var payStatus =commandPayDTO.getPayStatus();
        switch (payStatus){
            case "1":
                paymentStrategy =new SuccessfulPaymentStrategy();
                break;
            case "2":
                paymentStrategy =new FailedPaymentStrategy();
                break;
            case "3":
                paymentStrategy =new DuplicatePaymentStrategy();
                break;
        }
        if(paymentStrategy!=null){
            PaymentContext paymentContext = new PaymentContext(paymentStrategy);
            paymentContext.handlePaymentResult(commandPayDTO.getOrderId());
        }

    }
}

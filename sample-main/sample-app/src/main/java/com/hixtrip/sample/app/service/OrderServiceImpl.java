package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.constant.OrderStatusConstant;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.constant.PayStatusConstant;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Override
    public OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO, String userId) {
        // 判断库存是否
        Inventory inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        BigDecimal price = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        Integer amount = commandOderCreateDTO.getAmount();
        if(inventory.getSellableQuantity() < amount){
            throw new RuntimeException("库存不足");
        }
        Order order = new Order();
        // 根据特定规则生成
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setSkuId(commandOderCreateDTO.getSkuId());
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setMoney(new BigDecimal(commandOderCreateDTO.getAmount()).multiply(price));
        order.setPayStatus(PayStatusConstant.PAY_PENDING);
        order.setOrderStatus(OrderStatusConstant.ORDER_PENDING_PAYMENT);
        order.setDelFlag(0L);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        // 扣减库存
        boolean flag = inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), amount * 1L,-1L * amount,null);
        if(flag){
            orderDomainService.createOrder(order);
        }else {
            throw new RuntimeException("库存不足");
        }
        return OrderConvertor.INSTANCE.orderToOrderVO(order);
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.commandPayDTOToCommandPay(commandPayDTO);
        if(commandPay.getPayStatus().equals(PayStatusConstant.PAY_FAIL)){
            CommandPay failPay = new CommandPay();
            failPay.setOrderId(commandPay.getOrderId());
            failPay.setPayStatus(PayStatusConstant.PAY_FAIL);
            orderDomainService.orderPayFail(failPay);
        }else {
            boolean noPay = orderDomainService.isNoPay(commandPay.getOrderId(), PayStatusConstant.PAY_PENDING);
            if(noPay){
                CommandPay successPay = new CommandPay();
                successPay.setOrderId(commandPay.getOrderId());
                successPay.setPayStatus(PayStatusConstant.PAY_SUCCESS);
                successPay.setPayId(commandPay.getPayId());
                orderDomainService.orderPaySuccess(successPay);
            }else {
                CommandPay overPay = new CommandPay();
                overPay.setOrderId(commandPay.getOrderId());
                overPay.setPayStatus(PayStatusConstant.PAY_OVER);
                orderDomainService.orderPayOver(overPay);
            }
        }
    }
}

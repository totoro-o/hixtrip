package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;
import com.hixtrip.sample.client.sample.vo.PayVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
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

    @Autowired
    private PayContext payContext;

    @Override
    public OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        if (commandOderCreateDTO.getAmount() == null || commandOderCreateDTO.getAmount() < 1) {
            return OrderVO.builder().msg("商品数量异常").code("-1").build();
        }
        if (commandOderCreateDTO.getSkuId() == null || commandOderCreateDTO.getSkuId().isBlank()) {
            return OrderVO.builder().msg("商品参数缺失").code("-1").build();
        }
        //计算订单金额
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        BigDecimal totalPrice = skuPrice.multiply(new BigDecimal(String.valueOf(commandOderCreateDTO.getAmount())));

        Order order = OrderConvertor.INSTANCE.dtoToDomain(commandOderCreateDTO);
        order.setId("DD-123456789111222333444555666778");
        order.setSellerId("123");
        order.setDelFlag(0L);
        order.setMoney(totalPrice);
        order.setCreateBy(order.getUserId());
        //创建订单 预占库存
        Inventory inventory = inventoryDomainService.getInventory(order.getSkuId());
        Boolean withholding = inventory.withholding(order.getAmount());
        if (Boolean.TRUE.equals(withholding)) {
            Boolean ok = inventoryDomainService.changeInventory(inventory);
            if(Boolean.TRUE.equals(ok)){
                orderDomainService.createOrder(order);
                return OrderVO.builder().id(order.getId()).msg("订单创建成功").code("0").build();
            }
        }
        return OrderVO.builder().msg("订单创建失败 库存不足").code("-1").build();

    }

    @Override
    public PayVO payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.dtoToDomain(commandPayDTO);
        payDomainService.payRecord(commandPay);
        payContext.select(commandPay.getPayStatus()).payCallback(commandPay);
        return PayVO.builder().code(commandPay.getPayStatus()).id(commandPay.getOrderId()).build();
    }
}

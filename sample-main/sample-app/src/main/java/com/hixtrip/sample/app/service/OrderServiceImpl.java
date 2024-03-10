package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.strategy.PayCallbackContext;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.enums.OrderDelFlagEnum;
import com.hixtrip.sample.client.order.enums.PayCallbackStatusEnum;
import com.hixtrip.sample.client.order.enums.PayStatusEnum;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.client.order.vo.PayCallbaskVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.conf.InventoryConf;
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
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private PayCallbackContext payCallbackContext;

    /**
     * 创建订单
     * @param commandOderCreateDTO
     * @return
     */
    @Override
    public OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        String skuId = commandOderCreateDTO.getSkuId();
        Integer amount = commandOderCreateDTO.getAmount();
        String userId = commandOderCreateDTO.getUserId();
        // TODO: 2024/3/8 参数校验
        //查询SKU价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
        //扣减库存
        Inventory inventory = inventoryDomainService.getInventory(skuId);
        if (Inventory.withHolding(inventory, amount)) {//判断库存是否充足
            if (inventoryDomainService.changeInventory(inventory, InventoryConf.WITHHOLDING, amount)) {//预占用库存成功
                //生成订单
                Order createOrder = new Order();
                createOrder.setUserId(userId);
                createOrder.setSellerId("sellerId");
                createOrder.setAmount(amount);
                createOrder.setSkuId(skuId);
                createOrder.setDelFlag(OrderDelFlagEnum.EXIST.getCode());
                createOrder.setMoney(BigDecimal.valueOf(amount).multiply(skuPrice));
                Order order = orderDomainService.createOrder(createOrder);
                OrderVO orderVO = OrderConvertor.INSTANCE.orderToOrderVO(order);
                orderVO.setCreateCode("success");
                orderVO.setCreateMsg("创建订单成功");
                return orderVO;
            }

        }
        return OrderVO.builder().createCode("error").createMsg("库存不足").build();
    }

    @Override
    public PayCallbaskVO payCallbask(CommandPayDTO commandPayDTO) {
        // TODO: 2024/3/9  处理多次回调

        CommandPay commandPay = OrderConvertor.INSTANCE.payDTOToPay(commandPayDTO);
        //保存回调记录
        payDomainService.payRecord(commandPay);
        //执行支付回调策略
        PayCallbackStatusEnum payCallbackStatusEnum = PayCallbackStatusEnum.payCallbackStrategy(commandPayDTO.getPayStatus());
        return payCallbackContext.executeStrategy(payCallbackStatusEnum.getCallbackStrategy(), commandPay);
    }
}

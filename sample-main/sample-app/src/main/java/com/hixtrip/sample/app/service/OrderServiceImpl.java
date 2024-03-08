package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.app.convertor.SampleConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreateVO;
import com.hixtrip.sample.client.order.vo.PayVO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.strategy.PayActionContext;
import com.hixtrip.sample.domain.sample.model.Sample;
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
    private PayActionContext payActionContext;

    @Override
    public OrderCreateVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        Order order = OrderConvertor.INSTANCE.orderDTOToOrderDomain(commandOderCreateDTO);

        //创建订单
        orderDomainService.createOrder(order);

        return OrderConvertor.INSTANCE.orderToOrderCreateVO(order);
    }

    @Override
    public PayVO payCallBack(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.payDtoToPay(commandPayDTO);
        payActionContext.processPaymentCommand(commandPay);

        //支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
        return null;
    }
}

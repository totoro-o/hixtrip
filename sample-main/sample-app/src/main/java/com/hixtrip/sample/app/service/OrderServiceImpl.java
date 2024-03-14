package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.status.PayStatus;
import com.hixtrip.sample.infra.db.dataobject.CommandPayDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void createOrder(CommandOderCreateDTO sampleReq) {
        Order order = OrderConvertor.INSTANCE.commandOrderCreateTOrderDO(sampleReq);
        try {
            orderDomainService.createOrder(order);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ;
    }

    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CommandPayConvertor.INSTANCE.commandPayCreateTCommandPayDO(commandPayDTO);

        if (commandPay.getPayStatus().equals(PayStatus.PAY_SUCCESS.getName())) {
            return orderDomainService.orderPaySuccess(commandPay);
        } else if (commandPay.getPayStatus().equals(PayStatus.PAY_REAPET.getName())){
            return orderDomainService.orderPayFail(commandPay);
        } else if (commandPay.getPayStatus().equals(PayStatus.PAY_FAILED.getName())) {
            return orderDomainService.orderPayFail(commandPay);
        }

        return "回调状态异常";
    }


}

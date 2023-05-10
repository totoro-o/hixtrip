package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.util.RespCode;
import com.hixtrip.sample.util.RespResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public RespResult createOrder(OrderReq orderReq) {

        // 参数校验等。。。

        Order order = Order.builder().build();
        BeanUtils.copyProperties(orderReq, order);
        Boolean bo = orderDomainService.createOrder(order);

        return bo? RespResult.ok() : RespResult.error(RespCode.ERROR);
    }

    @Override
    public int updateAfterPayStatus(String id) {

        orderDomainService.orderPaySuccess(id);

        return 0;
    }


}

package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.OrderCreateReq;
import com.hixtrip.sample.client.order.OrderCreateVO;
import com.hixtrip.sample.domain.em.EnumPayPlatform;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderCreate;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.PayPlatform;
import com.hixtrip.sample.domain.pay.model.Pay;
import com.hixtrip.sample.infra.cache.Cache;
import com.hixtrip.sample.infra.pay.payplatform.PayPlatformContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单服务
 */
@Slf4j
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService domainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private Cache cache;


    @Override
    public OrderCreateVO create(Long userId, OrderCreateReq req) {
        //做一些基本的参数校验 ... 必填项目校验
        OrderCreate orderCreate = OrderConvertor.INSTANCE.reqToDomain(userId, req);
        Order order = domainService.createOrder(orderCreate);
        return OrderConvertor.INSTANCE.doMainToVO(order);
    }

    private static String createFinishPayKey(EnumPayPlatform enumPayPlatform, String payNo) {
        return payNo + "-FINISH-PAY-" + enumPayPlatform.getPlatform();
    }

    @Override
    public Object payCallback(EnumPayPlatform enumPayPlatform, Object params) {
        //获取支付平台
        PayPlatform payPlatform = PayPlatformContent.getPayPlatform(enumPayPlatform, params);
        Pay pay = payPlatform.getPay();
        Long orderId = Long.valueOf(pay.getNotifyParams());
        String payNo = pay.getPayNo();

        String cacheKey = createFinishPayKey(enumPayPlatform, payNo);
        //判断缓存 防止频繁调用
        if (cache.existKey(cacheKey)) {
            return payPlatform.notifyPlatform();
        }

        payDomainService.payRecord();

        boolean ok = payPlatform.paySuccess()
                ? domainService.orderPaySuccess(pay, orderId)
                : domainService.orderPayFail(pay, orderId);

        if (ok) {
            //缓存
            cache.set(createFinishPayKey(enumPayPlatform, payNo), payNo, 300L);
            return payPlatform.notifyPlatform();
        } else {
//            log.error("支付回调失败，payNo:{}", payNo);
            throw new RuntimeException("支付回调失败");
        }
    }


}

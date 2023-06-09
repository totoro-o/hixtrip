package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.OrderCreateReq;
import com.hixtrip.sample.client.order.OrderCreateVO;
import com.hixtrip.sample.domain.em.EnumPayPlatform;

import java.util.Map;

/**
 * 订单服务
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId 用户ID
     * @param req    请求参数
     * @return 订单ID
     */
    OrderCreateVO create(Long userId, OrderCreateReq req);


    /**
     * 支付回调
     */
    Object payCallback(EnumPayPlatform payPlatform, Object params);

}

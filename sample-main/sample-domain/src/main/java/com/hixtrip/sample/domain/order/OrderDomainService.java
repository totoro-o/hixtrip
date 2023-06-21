package com.hixtrip.sample.domain.order;

import org.springframework.stereotype.Component;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {
    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder() {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess() {
        //需要你在infra实现, 自行定义出入参
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail() {
        //需要你在infra实现, 自行定义出入参
    }
}

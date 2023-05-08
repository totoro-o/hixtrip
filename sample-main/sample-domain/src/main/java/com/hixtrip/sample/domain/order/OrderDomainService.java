package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.sample.model.MallOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderDomainService {
    /**
     * todo 需要实现  ->OK
     * 创建待付款订单
     */
    public MallOrder create(long userId, long skuId, long quantity) {
        return null;
    }

    /**
     * todo 需要实现   ->OK
     * 待付款订单支付成功
     */
    public void orderPaySuccess(Long orderId) {

    }

    /**
     * todo 需要实现   ->OK
     * 待付款订单支付失败
     */
    public void orderPayFail(Long orderId) {

    }
}

package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PaymentCallback;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *重复支付
 * @创建人 liyinglong
 * @创建时间 2024/3/7
 * @描述
 */
public class RepeatPaymentCallback implements PaymentCallback {

    @Override
    public void handlePaymentResult(Order order, String payStatus) {
        //TODO 判断状态 是否需要调用退款
        if("已支付".equals(payStatus)){
            // TODO 退款逻辑
            // TODO 记录日志
        }else if("支付失败".equals(payStatus)){
            // TODO 一直失败，再进行重试支付或者关闭订单
            // TODO 记录日志
        }
    }
}

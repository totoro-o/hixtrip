package com.hixtrip.sample.domain.order.constant;

/**
 * 订单状态常量
 */
public class OrderStatusConstant {


    /**
     * 新建订单  -- 待付款
     */
    public static String ORDER_PENDING_PAYMENT = "order_new";

    /**
     * 支付成功 -- 已订购
     */
    public static String ORDER_PURCHASE = "order_purchase";


    /**
     * 退款
     */
    public static String ORDER_REFUND = "order_refund";

}

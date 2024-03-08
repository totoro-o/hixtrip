package com.hixtrip.sample.domain.pay.constant;

/**
 * 支付订单状态常量
 */
public class PayStatusConstant {


    /**
     * 待支付
     */
    public static String PAY_PENDING = "pay_pending";

    /**
     * 支付成功
     */
    public static String PAY_SUCCESS = "pay_success";

    /**
     * 支付失败
     */
    public static String PAY_FAIL = "pay_fail";

    /**
     * 重复支付
     */
    public static String PAY_OVER = "pay_over";

    /**
     * 已退款
     */
    public static String PAY_REFUND = "pay_refund";

}

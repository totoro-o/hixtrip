package com.hixtrip.sample.domain.em;

/**
 * 订单状态
 */
public enum EnumOrderStatus {

    WAIT_PAY(0, "待付款"),
    WAIT_SEND_EXPRESS(1, "待发货"),
    WAIT_ACCEPT_EXPRESS(2, "待收货"),
    FINISH(3, "已完成"),

    CANCEL(4, "已取消");

    private int status;
    private String content;

    EnumOrderStatus(int status, String content) {
        this.status = status;
        this.content = content;
    }


    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}

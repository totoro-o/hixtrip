package com.hixtrip.sample.domain.sample.model;

public enum OrderStatus {
    WAIT_PAY("等待支付", 1),
    FAIL("失败", -1),
    SUCCESS("支付成功", 2),
    CANCEL("取消订单", 3);
    private String name;
    private int value;

    OrderStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package com.hixtrip.sample.app.config;

public enum PayCallbackType {
    PAY_SUCCESS("支付成功"),
    PAY_FAILURE("支付失败"),
    DUPLICATE_PAYMENT("重复支付");

    private String description;

    PayCallbackType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
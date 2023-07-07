package com.hixtrip.sample.domain.order.annotation;

public enum CallBackStatus {

    SUCCESS("success","支付成功"),
    FAIL("fail","支付失败"),
    REPEAT("repeat","重复支付");

    private String code;

    private String description;

    CallBackStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
}

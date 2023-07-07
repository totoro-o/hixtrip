package com.hixtrip.sample.domain.order.annotation;

public enum OrderStatus {
    NO_PAY("1","未支付"),
    PAY_SUCCESS("2","支付成功"),
    PAY_FAIL("3","支付失败");

    private String code;

    private String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
}

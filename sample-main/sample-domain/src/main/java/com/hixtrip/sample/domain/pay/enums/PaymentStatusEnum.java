package com.hixtrip.sample.domain.pay.enums;


public enum PaymentStatusEnum {

    SUCCEED(1, "支付成功"),

    FAILURE(2, "支付失败"),

    DUPLICATE(3, "重复支付"),

    ;

    private int code;
    private final String description;

    PaymentStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}


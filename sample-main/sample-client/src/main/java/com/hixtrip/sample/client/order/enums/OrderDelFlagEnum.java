package com.hixtrip.sample.client.order.enums;

public enum OrderDelFlagEnum {
    DELETE(1L),
    EXIST(0L)
    ;
    private Long code;

    private OrderDelFlagEnum(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }
}

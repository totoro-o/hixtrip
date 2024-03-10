package com.hixtrip.sample.client.order.enums;

public enum PayStatusEnum {
    UN_PAID("0"),//未付款
    PAID("1")//已付款
    ;
    private String code;

    private PayStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PayStatusEnum getPayStatusEnum(String code) {
        for (PayStatusEnum statusEnum : values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}

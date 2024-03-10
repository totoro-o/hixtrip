package com.hixtrip.sample.client.order.enums;

public enum PayCallbackStatusEnum {
    PAY_SUCCESS("paySuccess","paySuccessStrategy"),
    PAY_FAIL("payFail","payFailStrategy"),
    REPEATED_PAY("repeatedPay", "repeatedPayStrategy")
    ;

    private String status;
    private String callbackStrategy;

    private PayCallbackStatusEnum(String status, String callbackStrategy) {
        this.status = status;
        this.callbackStrategy = callbackStrategy;
    }

    public static PayCallbackStatusEnum payCallbackStrategy(String status) {
        for (PayCallbackStatusEnum statusEnum : values()) {
            if (statusEnum.status.equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public String getCallbackStrategy() {
        return callbackStrategy;
    }
}

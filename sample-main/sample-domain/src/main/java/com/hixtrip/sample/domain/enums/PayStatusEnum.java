package com.hixtrip.sample.domain.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    ORDER_TIMEOUT("-2"),
    PAY_FAILURE("-1"),
    UN_PAY("0"),
    PAY_SUCCESS("1"),
    PAY_DUPLICATE("2");

    private final String payStatus;

    PayStatusEnum(String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 根据支付状态标识获取枚举
     *
     * @param payStatus 支付状态
     * @return enum
     */
    public static PayStatusEnum getEnumFromStatus(String payStatus) {
        for (PayStatusEnum status : PayStatusEnum.values()) {
            if (status.getPayStatus().equals(payStatus)) {
                return status;
            }
        }
        throw new IllegalArgumentException("支付状态匹配失败 [" + payStatus + "]");
    }
}

package com.hixtrip.sample.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    /**
     * 未支付
     */
    NOT_PAY("not_pau"),

    /**
     * 支付成功
     */
    SUCCESS("success"),

    /**
     * 支付失败
     */
    FAIL("fail"),

    /**
     * 重复支付
     */
    DUPLICATE("duplicate");

    private final String value;

    /**
     * 字符串转换
     * @param payStatus
     * @return
     */
    public static PayStatusEnum find(String payStatus) {
        PayStatusEnum[] values = values();
        for (PayStatusEnum payStatusEnum : values) {
            if (payStatusEnum.getValue().equals(payStatus)) {
                return payStatusEnum;
            }
        }
        return null;
    }

    public static PayStatusEnum initStatus(){
        return PayStatusEnum.NOT_PAY;
    }
}

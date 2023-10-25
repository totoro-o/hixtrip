package com.hixtrip.sample.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付回调状态
 */
@AllArgsConstructor
@Getter
public enum OrderPayCallbackStatusEnum {
    SUCCESS("success", "支付成功"),
    FAILURE("failure", "支付失败"),
    DUPLICATE("duplicate", "重复支付");

    private final String code;
    private final String desc;

    public static OrderPayCallbackStatusEnum getByCode(String code) {
        for (OrderPayCallbackStatusEnum value : OrderPayCallbackStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}

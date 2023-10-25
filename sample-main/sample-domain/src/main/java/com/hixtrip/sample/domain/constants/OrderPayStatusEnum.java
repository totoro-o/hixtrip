package com.hixtrip.sample.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付状态
 */
@AllArgsConstructor
@Getter
public enum OrderPayStatusEnum {
    UNPAID("0", "待支付"),
    PAID("1", "已支付"),
    PAY_FAIL("2", "支付失败");

    private final String code;
    private final String desc;

    public static OrderPayStatusEnum getByCode(String code) {
        for (OrderPayStatusEnum value : OrderPayStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}

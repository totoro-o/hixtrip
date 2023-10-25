package com.hixtrip.sample.domain.pay.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateDate: 2023/10/25
 * @Author: ccj
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    SUCCESS("success"),

    FAIL("fail"),
    ;

    private String code;

    public static PayStatusEnum getByCode(String code) {
        for (PayStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("回调状态不合法：%s", code));
    }


}

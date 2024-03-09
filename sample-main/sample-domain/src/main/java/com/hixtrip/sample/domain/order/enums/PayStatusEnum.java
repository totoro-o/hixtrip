package com.hixtrip.sample.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {


    NO_PAY("noPay","0", "待支付"),
    SUCCESS("success","1", "支付成功"),
    FAIL("fail", "2","支付失败"),
    REPEAT("repeat", "3","支付失败"),



        ;

    private final String name;

    private final String code;

    private final String desc;


}

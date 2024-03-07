package com.hixtrip.sample.domain.pay.constants;

/**
 * @author gongjs
 */

public enum PayStatusEnum {



    /**
     * 待支付
     */
    WAIT("wait",0),
    /**
     * 支付成功
     */
    SUCCESS("success",1),
    /**
     * 支付失败
     */
    FAIL("fail",-1);

    String name;
    Integer value;

    PayStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}

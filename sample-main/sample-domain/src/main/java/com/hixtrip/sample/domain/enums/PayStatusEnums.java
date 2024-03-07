package com.hixtrip.sample.domain.enums;

public enum PayStatusEnums {
    /**
     * 支付状态
     */
    UN_PAY("1","未支付" ,null),
    PAY_FAIL("2","支付失败", "PayFailStrategy"),
    PAY_SUCCESS("3","支付成功", "PaySucceedStrategy"),
    PAID("4","已支付", "PayRepeatStrategy"),
            ;

    private String code;

    private String name;

    private String strategy;

    PayStatusEnums(String code, String name,String strategy) {
        this.code = code;
        this.name = name;
        this.strategy=strategy;
    }

    public static PayStatusEnums getByCode(String code) {
        PayStatusEnums[] values = PayStatusEnums.values();
        for (PayStatusEnums strategyEnum : values) {
            if (strategyEnum.getCode().equals(code)) {
                return strategyEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    public String getStrategy() {
        return strategy;
    }

}

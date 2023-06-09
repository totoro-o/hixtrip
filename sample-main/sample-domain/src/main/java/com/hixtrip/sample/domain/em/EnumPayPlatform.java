package com.hixtrip.sample.domain.em;

public enum EnumPayPlatform {

    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信");


    private int platform;
    private String content;

    EnumPayPlatform(int platform, String content) {
        this.platform = platform;
        this.content = content;
    }

    public static EnumPayPlatform getEnum(Integer payPlatform) {
        for (EnumPayPlatform enumPayPlatform : EnumPayPlatform.values()) {
            if (enumPayPlatform.getPlatform() == payPlatform) {
                return enumPayPlatform;
            }
        }
        return null;
    }

    public int getPlatform() {
        return platform;
    }

    public String getContent() {
        return content;
    }
}

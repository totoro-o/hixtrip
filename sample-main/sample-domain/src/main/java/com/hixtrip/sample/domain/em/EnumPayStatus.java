package com.hixtrip.sample.domain.em;

/**
 * 支付状态
 */
public enum EnumPayStatus {

    WAIT_PAY(0, "待付款"),
    PAY_SUCCESS(1, "支付成功"),
    PAY_FAIL(2, "支付失败");



    private int status;
    private String content;

    EnumPayStatus(int status, String content) {
        this.status = status;
        this.content = content;
    }


    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}

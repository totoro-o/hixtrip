package com.hixtrip.sample.client;

public enum ResultCode {

    SUCCESS(10000, "请求成功"),

    FAILED(10001, "操作失败"),
    ILLEGAL_ARGUMENT(10002, "非法的参数"),
    FAILED_ORDER(10010, "订单创建失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

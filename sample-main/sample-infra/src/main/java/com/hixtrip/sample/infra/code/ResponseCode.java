package com.hixtrip.sample.infra.code;

public enum ResponseCode {

    SUCCESS("success"),
    FAILED("failed");

    private String msg;

    ResponseCode(String msg) {
        this.msg = msg;
    }

    protected String getName(){
        return msg;
    }


}

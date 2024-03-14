package com.hixtrip.sample.domain.pay.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public enum PayStatus {


    PAY_SUCCESS("success"),
    PAY_FAILED("failed"),

    PAY_REAPET("repeat");

    private String code;

    PayStatus(String code) {
        this.code = code;
    }

    public String getName(){
        return this.code;
    }


}
package com.hixtrip.sample.common.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException{

    private int code;
    private String msg;

    public BizException(int code, String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }
}

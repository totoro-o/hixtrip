package com.hixtrip.sample.infra.exception;

public class BusinessException extends RuntimeException{

    private String errCode;
    private String errMessage;
    private String metadata;

    public <T> BusinessException(T data, String errCode, String metadata) {

    }

}

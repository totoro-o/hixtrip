package com.hixtrip.sample.infra.code;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseService<T> implements Serializable {

    private Integer status;
    private String msg;
    private T data;

    public ResponseService(Integer status) {

    }

    public ResponseService(String msg) {

    }

    public ResponseService(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static<T> ResponseService<T> createBySuccess(){
        return new ResponseService<T>(ResponseCode.SUCCESS.getName());
    }

    public static<T> ResponseService<T> createBySuccessMessage(String msg) {
        return new ResponseService<T>(msg);
    }

}

package com.hixtrip.sample.infra.utils;

import lombok.Data;

/**
 *
 * @param <T>
 */
@Data
public class ResponseObj<T> {

    private String message;

    private Integer code;

    private T data;

    private ResponseObj(String message ,Integer code, T data){
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseObj success(){
        return new ResponseObj(null,0, null);
    }

    public static <T> ResponseObj success(T data){
        return new ResponseObj(null,0, data);
    }

    public static <T> ResponseObj fail(String message){
        return new ResponseObj(message,-1,null);
    }

}

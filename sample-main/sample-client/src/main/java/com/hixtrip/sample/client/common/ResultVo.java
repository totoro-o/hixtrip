package com.hixtrip.sample.client.common;

import java.util.Map;

public class ResultVo {

    private int code;
    private String msg;
    private Map<String,Object> data;

    public static ResultVo success(Map<String,Object> data){
        return new ResultVo(200,"success",data);
    }

    public static ResultVo error(String msg){
        return new ResultVo(500,msg,null);
    }

    public ResultVo(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}

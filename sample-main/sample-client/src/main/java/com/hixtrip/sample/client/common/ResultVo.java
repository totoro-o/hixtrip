package com.hixtrip.sample.client.common;

import java.io.Serializable;
import java.util.Map;

public class ResultVo implements Serializable {

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

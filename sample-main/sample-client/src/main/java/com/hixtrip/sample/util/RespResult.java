package com.hixtrip.sample.util;

import java.io.Serializable;

/*****
 * @Author:
 * @Description:
 ****/
public class RespResult<T> implements Serializable {

    //响应数据结果集
    private T data;

    /**
     * 状态码
     * 20000 操作成功
     * 50000 操作失败
     */
    private Integer code;

    /***S
     * 响应信息
     */
    private String message;

    public RespResult() {
    }

    public RespResult(RespCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public RespResult(T data, RespCode resultCode) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    public static RespResult ok() {
        return new RespResult(null, RespCode.SUCCESS);
    }

    public static RespResult ok(Object data) {
        return new RespResult(data, RespCode.SUCCESS);
    }

    public static RespResult error() {
        return new RespResult(null, RespCode.ERROR);
    }

    public static RespResult error(String message) {
        return secByError(RespCode.ERROR.getCode(),message);
    }

    //自定义异常
    public static RespResult secByError(Integer code,String message) {
        RespResult err = new RespResult();
        err.setCode(code);
        err.setMessage(message);
        return err;
    }

    public static RespResult error(RespCode resultCode) {
        return new RespResult(resultCode);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

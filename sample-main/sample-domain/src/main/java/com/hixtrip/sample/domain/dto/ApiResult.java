package com.hixtrip.sample.domain.dto;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1234567890123456789L;

    private static final int NO_ERROR = 0;

    private int code; // 状态码
    private String message; // 返回消息
    private T data; // 返回数据

    public ApiResult() {
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(NO_ERROR, null, data);
    }

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> failed(int errorCode, String message) {
        return new ApiResult<>(errorCode, null, null);
    }

    public static <T> ApiResult<T> failed(int errorCode) {
        return failed(errorCode, null);
    }

    public boolean isSuccess() {
        return this.code == NO_ERROR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
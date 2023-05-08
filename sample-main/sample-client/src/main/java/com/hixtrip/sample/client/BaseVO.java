package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class BaseVO<T> {
    private int code;
    private String msg;
    private T data;

    public BaseVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseVO<T> success(T data) {
        return new BaseVO(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> BaseVO<T> fail(ResultCode errorCode) {
        return new BaseVO(errorCode.getCode(), errorCode.getMsg(), null);
    }
}

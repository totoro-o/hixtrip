package com.hixtrip.sample.client.order.vo;

import lombok.Data;

@Data
public class ResultVO<T> {
    private boolean success;
    private String message;
    private T data;

    public ResultVO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultVO<T> ok(T data) {
        return new ResultVO<>(true, null, data);
    }

    public static <T> ResultVO<T> ok() {
        return new ResultVO<>(true, null, null);
    }
}

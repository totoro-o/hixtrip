package com.hixtrip.sample.app.exception;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:29
 * app层执行异常
 */
public class AppException extends RuntimeException {

    private String message;

    public AppException(String message) {
        super(message);
        this.message = message;
    }
}

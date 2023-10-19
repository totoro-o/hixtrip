package com.hixtrip.sample.domain.common;

import org.springframework.stereotype.Component;

/**
 * <p> 身份凭证上下文，假设已经从请求中注入线程上下文中
 *
 * @author airness
 * @since 2023/10/18 21:50
 */
public class AuthenticationThreadHolder {

    private static final ThreadLocal<String> userThreadHolder = new ThreadLocal<>();


    public static String getUserId() {
        return userThreadHolder.get();
    }

    public static void setUserId(String userId) {
        userThreadHolder.set(userId);
    }

    public static void remove() {
        userThreadHolder.remove();
    }
}

package com.hixtrip.sample.app.service;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类说明
 *
 * @author gongjs
 * @date 2024/3/7
 */
@Component
public class PayContext {

    @Resource
    private Map<String, PayEvent> selectorMap = new HashMap<>();

    public PayContext(Map<String, PayEvent> selectorMap) {
        this.selectorMap.putAll(selectorMap);
    }

    public PayEvent select(String payStatus) {
        return selectorMap.get(payStatus);
    }
}

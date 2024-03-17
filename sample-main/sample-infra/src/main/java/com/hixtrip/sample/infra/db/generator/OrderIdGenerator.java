package com.hixtrip.sample.infra.db.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * order id生成器
 */
@Component
public class OrderIdGenerator implements IdGenerator {

    /**
     * 先简单实现一下
     *
     * @return
     */
    @Override
    public String build() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}

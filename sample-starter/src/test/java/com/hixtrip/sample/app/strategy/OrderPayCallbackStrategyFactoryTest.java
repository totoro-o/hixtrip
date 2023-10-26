package com.hixtrip.sample.app.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderPayCallbackStrategyFactoryTest {
    @Autowired
    private OrderPayCallbackStrategyFactory orderPayCallbackStrategyFactory;

    @Test
    void testGetStrategy() {
        assertTrue(orderPayCallbackStrategyFactory.getStrategy("failure").isPresent());
        assertTrue(orderPayCallbackStrategyFactory.getStrategy("success").isPresent());
        assertTrue(orderPayCallbackStrategyFactory.getStrategy("duplicate").isPresent());
        assertFalse(orderPayCallbackStrategyFactory.getStrategy("unknown").isPresent());
    }
}
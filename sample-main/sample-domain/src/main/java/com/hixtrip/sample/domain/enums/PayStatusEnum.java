package com.hixtrip.sample.domain.enums;

import com.hixtrip.sample.domain.pay.strategy.PayDupStrategy;
import com.hixtrip.sample.domain.pay.strategy.PayFailStrategy;
import com.hixtrip.sample.domain.pay.strategy.PayStrategy;
import com.hixtrip.sample.domain.pay.strategy.PaySucStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    /**
     * 支付成功
     */
    SUCCESS("success", PaySucStrategy.class),

    /**
     * 支付失败
     */
    FAIL("fail", PayFailStrategy.class),

    /**
     * 重复支付
     */
    DUPLICATE("duplicate", PayDupStrategy.class);

    private final String value;
    private final Class<? extends PayStrategy> strategyClass;

}

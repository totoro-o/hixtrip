package com.hixtrip.sample.domain.commodity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 商品领域服务
 */
@Component
public class CommodityDomainService {
    public BigDecimal getSkuPrice(String skuId) {
        return new BigDecimal(200);
    }
}

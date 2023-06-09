package com.hixtrip.sample.domain.commodity;

import com.hixtrip.sample.domain.commodity.model.Commodity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品领域服务
 */
@Component
public class CommodityDomainService {
    public BigDecimal getSkuPrice(String skuId) {
        return new BigDecimal(200);
    }


    public List<Commodity> listBySkuIdList(List<String> skuIdList) {
        return new ArrayList<>();
    }

}

package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.MallStockDO;

public interface MallStockMapper {

    void saveOrUpdate(MallStockDO stock);

    MallStockDO findOrderBySkuId(Long skuId);

    Integer updateStockBySkuId(Long quantity, Long skuId);
}

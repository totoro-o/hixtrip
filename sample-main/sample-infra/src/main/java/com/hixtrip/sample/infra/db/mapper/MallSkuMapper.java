package com.hixtrip.sample.infra.db.mapper;


import com.hixtrip.sample.infra.db.dataobject.MallSkuDO;

public interface MallSkuMapper {
    void saveOrUpdate(MallSkuDO sku);
}

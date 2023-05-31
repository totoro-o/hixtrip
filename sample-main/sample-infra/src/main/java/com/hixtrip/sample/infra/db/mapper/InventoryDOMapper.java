package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.InventoryDO;

public interface InventoryDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InventoryDO record);

    int insertSelective(InventoryDO record);

    InventoryDO selectByPrimaryKey(Long id);

    InventoryDO selectBySkuId(Long id);

    int updateByPrimaryKeySelective(InventoryDO record);

    int updateByPrimaryKey(InventoryDO record);
}
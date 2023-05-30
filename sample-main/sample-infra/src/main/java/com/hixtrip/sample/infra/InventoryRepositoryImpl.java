package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.InventoryDOMapper;
import com.hixtrip.sample.infra.db.mapper.OrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    InventoryDOMapper inventoryDOMapper;

    @Override
    public Inventory get(Long skuId) {
        InventoryDO inventoryDO = inventoryDOMapper.selectBySkuId(skuId);
        return InventoryDOConvertor.INSTANCE.doToDomain(inventoryDO);
    }

    @Override
    public Inventory save(Inventory inventory) {
        inventoryDOMapper.insert(InventoryDOConvertor.INSTANCE.domainToDo(inventory));
        return inventory;
    }

}

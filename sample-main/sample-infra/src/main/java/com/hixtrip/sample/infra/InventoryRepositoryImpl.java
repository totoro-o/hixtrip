package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRepositoryImpl implements InventoryRepository {
    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public Long getSellableQuantity(String skuId) {
        InventoryDO inventoryDO = inventoryMapper.findBySkuId(skuId);
        return inventoryDO.getSellableQuantity();
    }

    @Override
    public int updateInventory(Inventory inventory) {
        InventoryDO inventoryDO = inventoryMapper.findBySkuId(inventory.getSkuId());
        inventoryDO.setSellableQuantity(inventoryDO.getSellableQuantity());
        inventoryDO.setWithholdingQuantity(inventoryDO.getWithholdingQuantity() + inventory.getWithholdingQuantity());
        inventoryDO.setOccupiedQuantity(inventoryDO.getOccupiedQuantity() + inventory.getOccupiedQuantity());
        return inventoryMapper.updateQuantity(inventoryDO);
    }
}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public Long getInventory(String skuId) {
        InventoryDO item = inventoryMapper.getBySkuId();
        return Optional.ofNullable(item.getSellableQuantity()).orElse(0L);
    }

    @Override
    public Boolean updateOccupiedQuantity(String skuId, Long occupiedQuantity) {
        return inventoryMapper.updateOccupiedQuantity(skuId, occupiedQuantity) > 0;
    }

}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private InventoryMapper inventoryMapper;


    @Override
    public Inventory getBySkuId(String skuid) {
        return inventoryMapper.getBySkuId(skuid);
    }

    @Override
    public Integer changeInventory(String skuid,Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        return inventoryMapper.changeInventory(skuid,sellableQuantity,withholdingQuantity,occupiedQuantity);
    }

    @Override
    public Integer changeIntentoryOnPaySuccess(String skuId,Long withholdingQuantity) {
        return inventoryMapper.changeIntentoryOnPaySuccess(skuId,withholdingQuantity);
    }

    @Override
    public Integer changeIntentoryOnPayFail(String skuId,Long withholdingQuantity) {
        return inventoryMapper.changeIntentoryOnPayFail(skuId,withholdingQuantity);
    }
}

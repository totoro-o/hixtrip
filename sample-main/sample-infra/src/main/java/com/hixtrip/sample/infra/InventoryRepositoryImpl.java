package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRepositoryImpl implements InventoryRepository {


    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private InventoryDOConvertor inventoryDOConvertor;

    /**
     * 获取库存
     * @param skuId
     * @return
     */
    @Override
    public Inventory getInventory(String skuId) {
        InventoryDO inventoryDO = inventoryMapper.findBySkuId(skuId);
        // 使用InventoryDOConvertor将InventoryDO转换为Inventory
        return inventoryDOConvertor.INSTANCE.toInventory(inventoryDO);

    }

    /**
     * 更改库存呢
     * @param inventory
     */
    @Override
    public void changeInventory(Inventory inventory) {
        InventoryDO inventoryDO = inventoryDOConvertor.INSTANCE.toInventoryDO(inventory);
       int updateRows= inventoryMapper.updateInventory(inventoryDO);
        if (updateRows<0){
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public boolean deductInventory(String skuId, Integer skuNum) {
        InventoryDO inventoryDO  = inventoryMapper.findBySkuId(skuId);
        if (inventoryDO == null || inventoryDO.getSellableQuantity() < skuNum) {
            // 库存不足，扣减失败
            return false;
        }
        inventoryDO.setSellableQuantity(inventoryDO.getSellableQuantity() - skuNum);
        inventoryMapper.updateInventory(inventoryDO);
        // 库存扣减成功
        return true;
    }

    @Override
    public boolean rollbackInventory(String skuId, Integer skuNum) {
        InventoryDO inventoryDO = inventoryMapper.findBySkuId(skuId);
        if (inventoryDO == null) {
            // 库存不存在，回滚失败
            return false;
        }
        inventoryDO.setSellableQuantity(inventoryDO.getSellableQuantity() + skuNum);
        inventoryMapper.updateInventory(inventoryDO);
        // 库存回滚成功
        return true;

    }
}

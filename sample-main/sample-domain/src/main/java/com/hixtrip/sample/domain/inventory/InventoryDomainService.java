package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * 获取sku当前库存
     * @param skuId
     */
    public Long getInventory(String skuId) {
        if (skuId==null){
            //处理 skuId 为空的情况，根据实际需求进行相应处理
            throw new RuntimeException("sku不存在");
        }
        Inventory inventory = inventoryRepository.getInventory(skuId);
        return inventory != null ? inventory.getSellableQuantity() : 0;
    }

    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity 可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity 占用库存
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        Inventory inventory = inventoryRepository.getInventory(skuId);
        if (inventory==null){
            throw new RuntimeException("sku不存在");
        }
        if (inventory.getSellableQuantity()<sellableQuantity){
            throw new RuntimeException("库存不足");
        }
        Inventory request = new Inventory();
        request.setSkuId(skuId);
        request.setSellableQuantity(sellableQuantity);
        request.setWithholdingQuantity(withholdingQuantity);
        request.setOccupiedQuantity(occupiedQuantity);
       inventoryRepository.changeInventory(request);
        return true;
    }
}

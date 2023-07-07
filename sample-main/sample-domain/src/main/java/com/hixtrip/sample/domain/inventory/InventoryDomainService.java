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
     * @param inventory
     */
    public Long getInventory(Inventory inventory) {
        //todo 需要你在infra实现, 返回的领域对象自行定义
        return inventory.getSellableQuantityBySkuId();
    }

    public Inventory getBySkuId(String skuId){
        return inventoryRepository.getBySkuId(skuId);
    }

    /**
     * 修改库存
     * @param skuId
     * @param withholdingQuantity 预占库存
     * @return
     */
    public Boolean changeInventory(String skuId, Long withholdingQuantity) {
        //todo 需要你在infra实现，特别注意，需要处理一般并发场景，防止超卖。但不需要进行高并发设计。
        Inventory inventory = getBySkuId(skuId);
        Integer count  = inventory.changeInventory(withholdingQuantity);
        return count > 0;
    }
}

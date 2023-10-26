package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
@RequiredArgsConstructor
public class InventoryDomainService {
    private final InventoryRepository inventoryRepository;


    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        return inventoryRepository.getInventory(skuId).map(Inventory::getCurrentInventory).orElse(null);
    }

    /**
     * 修改库存, 原子性操作
     *
     * @param skuId                 skuId
     * @param sellableQuantityInc    可售库存增量
     * @param withholdingQuantityInc 预占库存增量
     * @param occupiedQuantityInc    占用库存增量
     * @return
     */
    public Boolean changeInventory(String skuId, Integer sellableQuantityInc, Integer withholdingQuantityInc, Integer occupiedQuantityInc) {
        Long result = inventoryRepository.incrementInventory(skuId, sellableQuantityInc, withholdingQuantityInc, occupiedQuantityInc);
        return Objects.equals(result, 1L);
    }
}

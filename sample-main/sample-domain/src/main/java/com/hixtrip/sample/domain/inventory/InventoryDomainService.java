package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.exception.InventoryDomainException;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        Optional<Inventory> optional = inventoryRepository.getInventory(skuId);
        if(optional.isEmpty()) {
            throw new InventoryDomainException("无法找到库存");
        }
        Inventory inventory = optional.get();
        if(inventory.getSellableQuantity() == 0L) {
            throw new InventoryDomainException("可售库存为0");
        }
        return Math.toIntExact(inventory.getSellableQuantity());
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存：不设定值
     * @param withholdingQuantity 预占库存：初期下单时设定值，sellableQuantity减，withholdingQuantity加
     * @param occupiedQuantity    占用库存：支付成功回调设定值，withholdingQuantity减， occupiedQuantity加
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        Optional<Inventory> optional = inventoryRepository.getInventory(skuId);
        if(optional.isEmpty()) {
            throw new InventoryDomainException("无法找到库存");
        }

        Inventory inventory = optional.get();
        if(withholdingQuantity != null) {
            inventory.updateWithholdingQuantity(withholdingQuantity);
        }
        if(occupiedQuantity != null) {
            inventory.updateOccupiedQuantity(occupiedQuantity);
        }
        return inventoryRepository.updateInventory(inventory);
    }
}

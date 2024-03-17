package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@AllArgsConstructor
@Component
public class InventoryDomainService {

    private final InventoryRepository inventoryRepository;


    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return inventoryRepository.getInventory(skuId).availableQuantity().intValue();
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        Boolean result = inventoryRepository.changeInventory(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
        if (result != null && result) {
            this.pushInventoryChangeEvent(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
        }
        return result;
    }

    /**
     * (模拟）抛出库存修改事件事件以便其他业务可能会需要监听
     * 这里考虑走消息队列，等。
     *
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     */
    private void pushInventoryChangeEvent(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        System.out.printf("发送库存修改事件。[%s][%s][%s][%s]%n", skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
    }

}

package com.hixtrip.sample.domain.inventory;

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
    InventoryRepository inventoryRepository;

    /**
     * 获取sku当前库存
     * @param skuId
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     */
    public Integer getInventory(String skuId,String type) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return inventoryRepository.getInventory(skuId,type);
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
        return inventoryRepository.changeInventory(skuId,sellableQuantity,withholdingQuantity,occupiedQuantity);
    }

    /**
     * 修改库存 同步
     * @param skuId
     * @param amount 数量
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     * @return
     */
    public  Integer changeInventory(String skuId, Integer amount, String type) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        return inventoryRepository.changeInventory(skuId,amount,type);
    }
}

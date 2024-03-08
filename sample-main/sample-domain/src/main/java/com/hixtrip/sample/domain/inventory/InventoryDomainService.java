package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.model.Sku;
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
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        // 模拟redis中skuId为1的商品（实际开发从接口中传参）
        skuId = "1";
        Integer inventory = inventoryRepository.getInventory(skuId);
        return inventory != null ? inventory : 0;
    }
    /**
     * 查询sku
     *
     * @param skuId
     */
    public Sku getSkuInfo(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return inventoryRepository.getSkuInfo(skuId);
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
        inventoryRepository.seInventory(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
        return true;
    }
}

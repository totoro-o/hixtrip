package com.hixtrip.sample.domain.inventory;

import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {
    public void getInventory(String skuId) {
        //todo 需要你在infra实现, 返回的领域对象自行定义
    }

    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现
        return true;
    }
}

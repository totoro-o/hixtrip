package com.hixtrip.sample.domain.inventory.model;

import lombok.Data;

/**
 * 库存领域对象
 */
@Data
public class Inventory {
    /**
     * skuId
     */
    private String skuId;

    /**
     * 可售库存
     */
    private Integer sellableQuantity;
    /**
     * 预占库存
     */
    private Integer withholdingQuantity;
    /**
     * 占用库存
     */
    private Integer occupiedQuantity;

    /**
     * 获取当前库存, 库存 = 可售库存 - 预占库存 - 占用库存
     *
     * @return 当前库存
     */
    public Integer getCurrentInventory() {
        if (sellableQuantity == null || withholdingQuantity == null || occupiedQuantity == null) {
            return null;
        }
        return sellableQuantity - withholdingQuantity - occupiedQuantity;
    }
}

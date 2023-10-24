package com.hixtrip.sample.infra.db.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 库存数据对象, 存储在Redis中
 */
@Data
@RedisHash("inventory")
public class InventoryDO {
    /**
     * skuId
     */
    @Id
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
}

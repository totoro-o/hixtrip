package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "inventory", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class InventoryDO extends SampleDO {

    /**
     * 库存ID
     */
    @TableId
    private Long inventoryId;

    /**
     * SKU
     */
    private String skuId;

    /**
     * 可售库存
     */
    private Long sellableQuantity;
    /**
     * 预占库存
     */
    private Long withholdingQuantity;





}

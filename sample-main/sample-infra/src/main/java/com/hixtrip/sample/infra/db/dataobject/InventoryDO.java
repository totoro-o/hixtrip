package com.hixtrip.sample.infra.db.dataobject;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "inventory", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class InventoryDO {


    private Integer inventoryId; //库存id

    private Integer skuId; //商品id

    /**
     * 可售数量
     */
    private Long sellableQuantity;

    /**
     * 预占数量
     */
    private Long withholdingQuantity;

    /**
     * 占用库存
     */
    private Long occupiedQuantity;
}

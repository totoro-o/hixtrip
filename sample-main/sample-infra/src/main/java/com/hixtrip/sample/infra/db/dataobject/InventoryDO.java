package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("inventory")
public class InventoryDO extends BaseDO {

    private String skuId;

    private Long totalQuantity;

    private Long sellableQuantity;

    private Long withholdingQuantity;

    private Long occupiedQuantity;

}

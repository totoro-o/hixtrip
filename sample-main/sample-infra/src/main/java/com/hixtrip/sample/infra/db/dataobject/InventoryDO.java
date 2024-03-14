package com.hixtrip.sample.infra.db.dataobject;


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
public class InventoryDO {


    private Integer inventoryId; //商品库存id

    private Integer productId; //商品id

    private Integer currentCnt; //当前商品数量

    private Integer lockCnt; //当前占用数据

    private Integer inTransitCnt; //在途库存数据(已下单未支付)
}

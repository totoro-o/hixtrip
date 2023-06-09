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
@TableName(value = "cart", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class CartDO extends SampleDO {

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 商品ID
     */
    private String skuId;

    /**
     * 商品数量
     */
    private Long num;

    // ...省略商品基本数据


}

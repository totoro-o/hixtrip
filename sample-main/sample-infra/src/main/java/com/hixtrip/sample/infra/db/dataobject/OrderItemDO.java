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
@TableName(value = "order_item", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderItemDO extends SampleDO {

    /**
     * 订单项ID
     */
    @TableId
    private Long orderItemId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 商品单价 (单位：分)
     */
    private Long commodityPrice;

    /**
     * 商品总价 (单位：分)
     */
    private Long commodityTotalPrice;

    /**
     * 商品基础数据 如名称、图片
     * ...
     */





}

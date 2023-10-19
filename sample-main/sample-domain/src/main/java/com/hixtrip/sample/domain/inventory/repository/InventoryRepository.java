package com.hixtrip.sample.domain.inventory.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取当前可锁定的库存
     * @param skuId 商品ID
     * @return 返回当前可用库存
     */
    @Nullable Integer getAvailableInventory(@NotNull String skuId);

    /**
     * 预占用库存;
     * 用户下单且在任何回调之前进行预扣库存
     * @param skuId 商品ID
     * @param quantity 锁定数量，只允许传入正整数，表示锁定可用库存
     * @return true表示成功锁定库存
     */
    boolean changeWithholdingQuantity(@NotNull String skuId, Integer quantity);

    /**
     * 修改库存
     * 订单结束时
     * @param skuId 商品ID
     * @param quantity 锁定数量，正数表示增加，负数表示扣减
     * @return true表示成功锁定库存
     */
    boolean changeSellableQuantity(@NotNull String skuId, Integer quantity);

    /**
     * 占用库存
     * 订单支付完成后进行实际的库存占用
     * @param skuId 商品ID
     * @param quantity 锁定数量，只允许传入正整数，表示锁定可用库存
     * @return true表示成功锁定库存
     */
    boolean changOccupiedQuantity(@NotNull String skuId, Integer quantity);
}

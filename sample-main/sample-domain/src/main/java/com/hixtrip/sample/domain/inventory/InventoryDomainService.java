package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
@RequiredArgsConstructor
public class InventoryDomainService {


    private final InventoryRepository repository;


    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        return repository.getAvailableInventory(skuId);
    }

    /**
     * 修改库存
     * @implNote 由于题目限制无法增加领域层API，因此这里将修改库存的方法拆分
     * @param skuId 商品ID
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return true表示操作成功
     */
    // 库存量个人觉得使用Int类型足矣，单个商品库存难以达到亿级别，而int最大支持2147483647
    public @NotNull Boolean changeInventory(String skuId, Integer sellableQuantity, Integer withholdingQuantity, Integer occupiedQuantity) {
        // 这里调用了基础层的lua脚本实现，每个操作都是原子的，不需要加锁即可实现并发，若并发量过高可考虑库存分片
        if (Objects.nonNull(sellableQuantity) && sellableQuantity != 0) {
            return repository.changeSellableQuantity(skuId, sellableQuantity);
        } else if (Objects.nonNull(withholdingQuantity) && withholdingQuantity != 0) {
            return repository.changeWithholdingQuantity(skuId, withholdingQuantity);
        } else if (Objects.nonNull(occupiedQuantity) && occupiedQuantity != 0) {
            return repository.changOccupiedQuantity(skuId, occupiedQuantity);
        }
        return Boolean.FALSE;
    }
}

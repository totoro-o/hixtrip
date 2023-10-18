package com.hixtrip.sample.infra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.constants.InventoryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取可售库存
     *
     * @param skuId
     * @return
     */
    @Override
    public Integer getInventory(String skuId) {
        // 从Redis读取库存
        String key = InventoryConstants.INVENTORY_REDIS_KEY_PREFIX + skuId;
        String inventoryJsonStr = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(inventoryJsonStr)) {
            return 0;
        }
        JSONObject inventory = JSONObject.parseObject(inventoryJsonStr);
        Integer totalInventory = inventory.getInteger(InventoryConstants.TOTAL);
        Integer sellInventory = inventory.getInteger(InventoryConstants.SELL);
        if (totalInventory != null) {
            return Objects.isNull(sellInventory) ? 0 : totalInventory - sellInventory;
        } else {
            return 0;
        }
    }

    /**
     * 更改库存
     *
     * @param skuId
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, Long changeInventory) {
        // 此处考虑单项目情况下加锁，分布式可以使用Redis实现分布式锁
        // 根据商品ID加锁
        synchronized(skuId.intern()) {
            // 获取库存信息
            String key = InventoryConstants.INVENTORY_REDIS_KEY_PREFIX + skuId;
            String inventoryJsonStr = redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(inventoryJsonStr)) {
                return false;
            }
            JSONObject inventory = JSONObject.parseObject(inventoryJsonStr);
            // 判断库存是否能够扣除
            Integer totalInventory = inventory.getInteger(InventoryConstants.TOTAL);
            Integer sellInventory = inventory.getInteger(InventoryConstants.SELL);
            if (Objects.isNull(totalInventory)) {
                return false;
            }
            if (Objects.isNull(sellInventory)) {
                sellInventory = 0;
            }
            // 不允许超卖
            if (totalInventory < sellInventory + changeInventory) {
                return false;
            }
            // 更新库存
            inventory.put(InventoryConstants.SELL, sellInventory + changeInventory);
            inventoryJsonStr = JSON.toJSONString(inventory);
            redisTemplate.opsForValue().set(key, inventoryJsonStr);
            return true;
        }
    }
}

package com.hixtrip.sample.infra;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.hixtrip.sample.infra.constants.InventoryConstants.*;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取可售库存
     *
     * @param skuId
     * @return 可售库存数量
     */
    @Override
    public Integer getInventory(String skuId) {
        // 获取redis中的库存信息
        String cacheKey = INVENTORY_INFO_KEY_PREFIX + skuId;
        String inventoryJsonStr = String.valueOf(redisTemplate.opsForValue().get(cacheKey));
        if (StringUtils.isBlank(inventoryJsonStr)) {
            return 0;
        }
        JSONObject inventory = JSONObject.parseObject(inventoryJsonStr);
        // 返回可售库存
        return inventory.getInteger(SELLABLE_QUANTITY);
    }

    /**
     * 修改库存
     * @param skuId 商品ID
     * @param sellableQuantity 可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity 占用库存
     * @return Boolean
     */
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        synchronized (skuId.intern()) {
            // 获取缓存中的库存信息
            String cacheKey = INVENTORY_INFO_KEY_PREFIX + skuId;
            String inventoryJsonStr = String.valueOf(redisTemplate.opsForValue().get(cacheKey));
            if (StringUtils.isBlank(inventoryJsonStr)) {
                return false;
            }
            // 更新缓存
            JSONObject inventory = JSONObject.parseObject(inventoryJsonStr);
            // 可售库存小于0，超卖
            if (inventory.getInteger(SELLABLE_QUANTITY) + sellableQuantity < 0) {
                return false;
            }
            inventory.put(SELLABLE_QUANTITY, inventory.getInteger(SELLABLE_QUANTITY) + sellableQuantity);
            inventory.put(WITHHOLDING_QUANTITY, inventory.getInteger(WITHHOLDING_QUANTITY) + withholdingQuantity);
            inventory.put(OCCUPIED_QUANTITY, inventory.getInteger(OCCUPIED_QUANTITY) + occupiedQuantity);

            redisTemplate.opsForValue().set(cacheKey, inventory.toJSONString());
            return true;
        }
    }
}

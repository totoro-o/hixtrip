package com.hixtrip.sample.infra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hixtrip.sample.common.constant.CacheKey;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Long getInventory(String skuId) {
        // 从Redis读取库存
        String cacheKey = String.format(CacheKey.PRODUCT_INVENTORY_SKU, skuId);
        String inventoryJsonStr = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isEmpty(inventoryJsonStr)) {
            return 0L;
        }
        //此处map替代inventoryJsonStr对象
        Map<String,Object> inventoryJsonStrMap = JSON.parseObject(inventoryJsonStr, Map.class);
        Integer totalQuantity = (Integer)inventoryJsonStrMap.get("totalQuantity");
        Integer sellableQuantity =  (Integer)inventoryJsonStrMap.get("sellableQuantity");
        if (totalQuantity != null) {
            return (long) (sellableQuantity - totalQuantity);
        }

        return 0L;
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        // 单机情况使用synchronized，分布式场景可使用redisson方便解决
        synchronized (skuId.intern()) {
            String cacheKey = String.format(CacheKey.PRODUCT_INVENTORY_SKU, skuId);
            String inventoryJsonStr = redisTemplate.opsForValue().get(cacheKey);
            if (StringUtils.isEmpty(inventoryJsonStr)) {
                return false;
            }
            //此处map替代inventoryJsonStr对象
            Map<String,Object> inventoryJsonStrMap = JSON.parseObject(inventoryJsonStr, Map.class);
            Integer totalQuantity = (Integer)inventoryJsonStrMap.get("totalQuantity");
            Integer sellableQuantityNew =  (Integer)inventoryJsonStrMap.get("sellableQuantity");
            if ( sellableQuantityNew + withholdingQuantity >  totalQuantity ) {
                return false;
            }
            // 更新库存
            inventoryJsonStrMap.put("sellableQuantity", totalQuantity - withholdingQuantity);
            inventoryJsonStr = JSON.toJSONString(inventoryJsonStrMap);
            redisTemplate.opsForValue().set(cacheKey, inventoryJsonStr);
            return true;
        }
    }
}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Inventory getInventory(String skuId) {

        Long sellable = Long.valueOf(redisTemplate.boundValueOps(RedisConstant.PREFIX_KEY + RedisConstant.SELLABLE_QUANTITY_KEY + skuId).get().toString());
        Long withholding = Long.valueOf(redisTemplate.boundValueOps(RedisConstant.PREFIX_KEY + RedisConstant.WITHHOLDING_QUANTITY_KEY + skuId).get().toString());
        Long occupied = Long.valueOf(redisTemplate.boundValueOps(RedisConstant.PREFIX_KEY + RedisConstant.OCCUPIED_QUANTITY_KEY + skuId).get().toString());

        return Inventory.builder().skuId(skuId).sellableQuantity(Long.valueOf(sellable.toString()))
                .withholdingQuantity(Long.valueOf(withholding.toString())
                ).occupiedQuantity(Long.valueOf(occupied.toString())).build();
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {

        List<String> keyList = Arrays.asList(RedisConstant.PREFIX_KEY + RedisConstant.SELLABLE_QUANTITY_KEY + skuId,
                RedisConstant.PREFIX_KEY + RedisConstant.WITHHOLDING_QUANTITY_KEY + skuId,
                RedisConstant.PREFIX_KEY + RedisConstant.OCCUPIED_QUANTITY_KEY + skuId);

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(RedisConstant.SCRIPT, Boolean.class);

        // 执行 Lua 脚本
        Boolean result = redisTemplate.execute(redisScript,keyList,sellableQuantity,withholdingQuantity,occupiedQuantity);

        return result != null ? result : false;

    }
}

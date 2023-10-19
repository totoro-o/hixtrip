package com.hixtrip.sample.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.script.ClasspathRedisScriptLoader;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {
    public static final String SKU_KEY = "simple:sku:inventory:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RedisScript<Integer> changeWithholdingQuantityScript;
    private final RedisScript<Integer> changeOccupiedQuantityScript;
    private final RedisScript<Integer> changeSellableQuantityScript;


    public InventoryRepositoryImpl(
            @Qualifier("redisScriptTemplate") RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        // 初始化脚本
        this.changeWithholdingQuantityScript =
                ClasspathRedisScriptLoader.loadScript("lua/changeWithholdingQuantity.lua", Integer.class);
        this.changeOccupiedQuantityScript =
                ClasspathRedisScriptLoader.loadScript("lua/changeOccupiedQuantity.lua", Integer.class);
        this.changeSellableQuantityScript =
                ClasspathRedisScriptLoader.loadScript("lua/changeSellableQuantity.lua", Integer.class);
    }


    /**
     * 构建
     * @param skuId 商品 ID
     * @return 返回redis商品库存Key
     */
    private String getSkuKey(String skuId) {
        return SKU_KEY + skuId;
    }

    /**
     * 内部映射实体方法
     * @param string 来自Redis的内容
     * @return 返回实体
     */
    private @NotNull InternalSkuInventory mapperToEntity(@NotNull String string) {
        try {
            return objectMapper.readValue(string, InternalSkuInventory.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("库存基础层：解析商品库存Json失败", e);
        }
    }


    @Override
    public @Nullable Integer getAvailableInventory(@NotNull String skuId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getSkuKey(skuId)))
                .map(this::mapperToEntity)
                // 可用库存 = 可售库存 - 预占库存 - 占用库存
                .map(e -> e.getSellableQuantity() - e.getWithholdingQuantity() - e.getOccupiedQuantity())
                .orElse(null);
    }

    @Override
    public boolean changeWithholdingQuantity(@NotNull String skuId, Integer quantity) {
        Integer result = redisTemplate.execute(changeWithholdingQuantityScript, Collections.singletonList(getSkuKey(skuId)), quantity);
        // 记录操作日志
        return Objects.nonNull(result) && result >= 0;
    }

    @Override
    public boolean changeSellableQuantity(@NotNull String skuId, Integer quantity) {
        Integer result = redisTemplate.execute(changeSellableQuantityScript, Collections.singletonList(getSkuKey(skuId)), quantity);
        // 记录操作日志
        return Objects.nonNull(result) && result >= 0;
    }

    @Override
    public boolean changOccupiedQuantity(@NotNull String skuId, Integer quantity) {
        Integer result = redisTemplate.execute(changeOccupiedQuantityScript, Collections.singletonList(getSkuKey(skuId)), quantity);
        // 记录操作日志
        return Objects.nonNull(result) && result >= 0;
    }


    /**
     * 内部使用的库存Redis数据结构
     */
    @Data
    public static class InternalSkuInventory {
        private Integer sellableQuantity;
        private Integer withholdingQuantity;
        private Integer occupiedQuantity;
    }
}

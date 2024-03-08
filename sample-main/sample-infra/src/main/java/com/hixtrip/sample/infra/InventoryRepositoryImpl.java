
package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Inventory getInventory(String skuId) {

        InventoryDO inventoryDO = (InventoryDO) redisTemplate.opsForValue().get(skuId);

        return InventoryDOConvertor.INSTANCE.doToDomain(inventoryDO);

    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        if(sellableQuantity>0){
            return releaseReservedStock(skuId,sellableQuantity);
        } else if(withholdingQuantity>0){
            return reserveStock(skuId,withholdingQuantity);
        } else if(occupiedQuantity>0){
            return occupyStock(skuId,occupiedQuantity);
        }
        //提前预热库存到Redis，避免在秒杀开始时从数据库加载导致的延迟。
        //设置合理的Redis过期时间，防止缓存与数据库的数据长时间不一致。
        //结合后台异步任务定期同步缓存与数据库的库存数据，保持一致性。
        //对于极端高并发场景，还可以考虑引入分布式锁或者队列服务控制秒杀请求的流量，比如使用Redis的setnx命令配合过期时间实现分布式锁。
        return false;
    }

    /**
     * 增加预占库存
     */
    public boolean reserveStock(String skuId, Long quantity) {
        String sellableStockKey = "product:" + skuId + ":sellable_stock";
        String reservedStockKey = "product:" + skuId + ":reserved_stock";

        // 使用Lua脚本原子性地转移库存
        String script = "local sellableStock = redis.call('get', KEYS[1]) " +
                "local reservedStock = redis.call('get', KEYS[2]) " +
                "if tonumber(sellableStock) >= tonumber(ARGV[1]) then " +
                "   redis.call('decrby', KEYS[1], ARGV[1]) " +
                "   redis.call('incrby', KEYS[2], ARGV[1]) " +
                "   return 1 " +
                "else " +
                "   return 0 " +
                "end";

        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(sellableStockKey, reservedStockKey),
                quantity.toString()
        );

        return result == 1L;
    }

    /**
     * 确认订单并占用库存（从预占库存转为占用库存）
     */
    public boolean occupyStock(String skuId, Long quantity) {
        String reservedStockKey = "product:" + skuId + ":reserved_stock";
        String occupiedStockKey = "product:" + skuId + ":occupied_stock";

        // 类似reserveStock，使用Lua脚本原子性地转移库存
        String script = "local reservedStock = redis.call('get', KEYS[1]) " +
                "local occupiedStock = redis.call('get', KEYS[2]) " +
                "if tonumber(reservedStock) >= tonumber(ARGV[1]) then " +
                "   redis.call('decrby', KEYS[1], ARGV[1]) " +
                "   redis.call('incrby', KEYS[2], ARGV[1]) " +
                "   return 1 " +
                "else " +
                "   return 0 " +
                "end";

        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(reservedStockKey, occupiedStockKey),
                quantity.toString()
        );

        return result == 1L;
    }

    /**
     * 释放预占库存（例如：订单超时）
     */
    public boolean releaseReservedStock(String skuId, Long quantity) {
        String reservedStockKey = "product:" + skuId + ":reserved_stock";
        String sellableStockKey = "product:" + skuId + ":sellable_stock";

        // 使用Lua脚本原子性地将预占库存归还给可售库存
        String script = "redis.call('incrby', KEYS[1], ARGV[1]) " +
                "redis.call('decrby', KEYS[2], ARGV[1])";

        redisTemplate.execute(
                new DefaultRedisScript<>(script),
                Arrays.asList(sellableStockKey, reservedStockKey),
                quantity.toString()
        );

        return Boolean.TRUE;
    }
}

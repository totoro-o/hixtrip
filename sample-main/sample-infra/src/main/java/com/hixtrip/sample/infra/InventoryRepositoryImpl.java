package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    private static final String STOCK_KEY_PREFIX = "stock:";//可售库存key

    @Override
    public Integer getInventory(String skuId) {
        // 缓存中取库存数量
        Object inventory = redisTemplate.opsForValue().get(STOCK_KEY_PREFIX + skuId);
        return inventory == null ? 0 : Integer.parseInt((String) inventory);
    }

    /**
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @param amount
     * @return
     */
    // 加锁的方式，实现并发超卖问题 会导致线程阻塞，性能变慢，无锁实现方式，需用Lua 脚本实现
    @Override
    public  Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity, Integer amount) {
        String lockedStockKey = STOCK_KEY_PREFIX + skuId + ":locked";//预占库存key
        String soldStockKey = STOCK_KEY_PREFIX + skuId + ":sold";//占用库存Key
        List<String> keyList = Arrays.asList(STOCK_KEY_PREFIX, lockedStockKey, soldStockKey).stream().collect(Collectors.toList());
        List<String> valueList = Arrays.asList(sellableQuantity.toString(), withholdingQuantity.toString(), occupiedQuantity.toString(), amount.toString()).stream().collect(Collectors.toList());

        String script = "local stockKey = KEYS[1]   -- 可售库存key  \n" +
                        "local lockedStockKey = KEYS[2]  -- 预占库存key  \n" +
                        "local soldStockKey = KEYS[3]  -- 占用库存key  \n" +
                        "local sellableQuantity = ARGV[1]    -- 可售库存更新值  \n" +
                        "local withholdingQuantity = ARGV[2]    -- 预占库存更新值  \n" +
                        "local occupiedQuantity = ARGV[3]    -- 占用库存更新值  \n" +
                        "local amount = ARGV[4]    -- 扣减数量  \n" +

                        "  \n" +
                        "-- 获取当前可售库存数量  \n" +
                        "local currentStock = redis.call('get', stockKey)  \n" +
                        "  \n" +
                        "if currentStock then  \n" +
                        "    currentStock = tonumber(currentStock)  \n" +
                        "      \n" +
                        "    -- 检查库存是否足够  \n" +
                        "    if currentStock >= amount then  \n" +
                        "        -- 使用Redis的事务功能，确保库存扣减和设置用户已扣减状态的原子性  \n" +
                        "        redis.call('INCRBY ', stockKey, sellableQuantity)  \n" +
                        "        redis.call('INCRBY ', lockedStockKey, withholdingQuantity)  \n" +
                        "        redis.call('INCRBY ', soldStockKey, occupiedQuantity)  \n" +
                        "        return true  -- 扣减成功  \n" +
                        "    else  \n" +
                        "        return false  -- 库存不足  \n" +
                        "    end  \n" +
                        "else  \n" +
                        "    return false  -- 库存不存在  \n" +
                        "end";
        RedisScript<Long> luaScript = RedisScript.of(script, Long.class);
        Long result = redisTemplate.execute(luaScript, keyList, valueList);

        return result != null && result == 1;
    }

//    /**
//     * @param skuId
//     * @param sellableQuantity    可售库存
//     * @param withholdingQuantity 预占库存
//     * @param occupiedQuantity    占用库存
//     * @return
//     */
//    // 加锁的方式，实现并发超卖问题 会导致线程阻塞，性能变慢，无锁实现方式，需用Lua 脚本实现
//    @Override
//    public synchronized Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
//
//        String lockedStockKey = STOCK_KEY_PREFIX + skuId + ":locked";//预占库存key
//        String soldStockKey = STOCK_KEY_PREFIX + skuId + ":sold";//占用库存Key
//        // 更新可售库存
//        if (null != sellableQuantity) {
//            redisTemplate.opsForValue().increment(STOCK_KEY_PREFIX + skuId, sellableQuantity);
//        }
//        // 更新预占库存
//        if (null != withholdingQuantity) {
//            redisTemplate.opsForValue().increment(lockedStockKey, withholdingQuantity);
//        }
//        // 更新占用库存
//        if (null != occupiedQuantity) {
//            redisTemplate.opsForValue().increment(soldStockKey, occupiedQuantity);
//        }
//        return true;
//    }
}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ReentrantLock reentrantLock;


    @Override
    public BigInteger getInventory(String skuId) {
        try {
            //尝试从缓存内获取商品库存
            Object inventoryCache = redisTemplate.opsForValue().get(skuId);
            if (Objects.isNull(inventoryCache)) {
                //避免缓存击穿
                if (reentrantLock.tryLock()) {
                    //查询商品库存，写入缓存内
                    int inventory = 10;
                    redisTemplate.opsForValue().set(skuId, String.valueOf(inventory));
                } else {
                    //让没有获取到锁的请求稍等一会再进行重试
                    Thread.sleep(100);
                }
                return getInventory(skuId);
            }
            return new BigInteger(inventoryCache.toString());
        } catch (Exception e) {
            e.printStackTrace();
            reentrantLock.unlock();
            throw new RuntimeException("获取商品库存失败");
        }
    }

    @Override
    public void changeWithholdingQuantity(String skuId, Long sellableQuantity, Long withholdingQuantity) {
        //更新缓存内库存数据
        BigInteger inventory = new BigInteger(sellableQuantity.toString());
        BigInteger amount = new BigInteger(withholdingQuantity.toString());
        BigInteger subtractInventory = inventory.subtract(amount);
        redisTemplate.opsForValue().set(skuId, subtractInventory);
    }

    @Override
    @Transactional
    public void changeOccupiedQuantity(String skuId, Long occupiedQuantity) {
        //数据库更新商品库存
    }
}

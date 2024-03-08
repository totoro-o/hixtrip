package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.common.constant.RedisConstants;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取当前skuId库存
     * @param skuId
     * @return
     */
    @Override
    public Integer getInventory(String skuId) {
        Integer stockCount = (Integer)redisTemplate.opsForHash().get(RedisConstants.getStockCount(), skuId);
        if(stockCount == null){
            // 从数据库中查询出数量再放入缓存中
            stockCount = 99;// 假设查询出了99个可售库存
        }
        return stockCount;
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        int k = 0;
        while(true){
            Boolean lock = redisTemplate.opsForHash().putIfAbsent(RedisConstants.getStockCountUpdateLock(), skuId, 1);
            if(lock){// 如果上锁成功，直接跳出循环
                break;
            }
            if(k > 100){
                throw new RuntimeException("服务器处理超时，请稍后重试");
            }
            try{
                k++;
                Thread.sleep(500 * k);
            } catch (InterruptedException e) {
                throw new RuntimeException("库存修改，线程睡眠异常",e);
            }

        }
        try{
            // 扣减库存
            Integer inventory = getInventory(skuId);
            redisTemplate.opsForHash().put(RedisConstants.getStockCount(),skuId,inventory-withholdingQuantity);
        }catch (Exception e){
            throw new RuntimeException("扣减库存异常",e);
        }finally {
            redisTemplate.opsForHash().delete(RedisConstants.getStockCountUpdateLock(),skuId);// 释放锁
        }
        return null;
    }
}

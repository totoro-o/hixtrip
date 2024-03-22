package com.hixtrip.sample.infra;

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


    @Override
    public Integer getInventory(String skuId) {
        var inventory=redisTemplate.opsForValue().get(skuId);
        return inventory == null ? 0 :  new Integer(inventory.toString());
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {

        Long  inventory = sellableQuantity -occupiedQuantity;
        if(Long.compare(inventory,0l)<0){
            //库存不足
            return false;
        }

        //获取最新的sku库存
        var inventoryNew = this.getInventory(skuId);
        //如果不一致，返回扣减失败
        if (!inventoryNew.equals(sellableQuantity)) {
            return false;
        }

        //进行库存的扣减动作
        redisTemplate.opsForValue().set(skuId,inventory);
        return true;
    }

}

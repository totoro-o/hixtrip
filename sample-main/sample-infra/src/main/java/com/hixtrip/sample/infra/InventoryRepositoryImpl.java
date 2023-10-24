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

    /**
     * 获取sku当前库存
     * @param skuId
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     * @return
     */
    @Override
    public Integer getInventory(String skuId, String type) {
        //缓存key = 商品编号+-+类型
        String key = skuId+"-"+type;
        //获取数据
        Object obj = redisTemplate.opsForValue().get(key);
        if(obj==null){
            return 0;
        }
        return Integer.valueOf(obj.toString());
    }


    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //缓存key = 商品编号+-+类型
        if(sellableQuantity!=null){
            String sellableKey = skuId+"-sellable";
            redisTemplate.opsForValue().set(sellableKey, sellableQuantity);
        }
        if(withholdingQuantity!=null){
            String withholdingKey = skuId+"-withholding";
            redisTemplate.opsForValue().set(withholdingKey, withholdingQuantity);
        }
        if(occupiedQuantity!=null){
            String occupiedKey = skuId+"-occupied";
            redisTemplate.opsForValue().set(occupiedKey,occupiedQuantity);
        }
        return true;
    }

    /**
     * 修改库存 同步
     * @param skuId
     * @param amount 计算数量
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     * @return 剩余库存
     */
    @Override
    public Integer changeInventory(String skuId, Integer amount, String type) {
        //缓存key = 商品编号+-+类型
        String key = skuId+"-"+type;
        //每个商品库存的类型同步
        synchronized (key){
            //获取数据
            Object obj = redisTemplate.opsForValue().get(key);
            if(obj==null){
                return -1;
            }
            Integer quantityOld = Integer.valueOf(obj.toString());
            //扣除库存时需要计算库存是否充足
            if(amount<0 && Math.abs(amount) > quantityOld){
                return -1;
            }
            Integer quantityNew = quantityOld+amount;
            redisTemplate.opsForValue().set(key, quantityNew);
            return quantityNew;
        }
    }
}

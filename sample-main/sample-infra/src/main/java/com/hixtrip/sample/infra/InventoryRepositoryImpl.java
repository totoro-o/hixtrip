package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Integer getInventory(String skuId) {
        RLock lock = redissonClient.getLock(skuId);
        boolean locked = false;
        try {
            locked = lock.tryLock(getWaitMills(), getLeaseMills(), TimeUnit.MILLISECONDS);
            // 加锁失败
            if (!locked) {

                failover(skuId);
            }
            Integer inventory = (Integer) redisTemplate.opsForValue().get(skuId);
            if (ObjectUtils.isEmpty(inventory)) {
                return 0;
            }
            return inventory;
        } catch (RuntimeException e) {
            // 修改为抛出自定义异常
            throw new RuntimeException("[加锁失败:" + skuId + "]");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {

        RLock lock = redissonClient.getLock(skuId);
        boolean locked = false;
        try {
            locked = lock.tryLock(getWaitMills(), getLeaseMills(), TimeUnit.MILLISECONDS);
            // 加锁失败
            if (!locked) {
                failover(skuId);
            }

            Integer inventory = (Integer) redisTemplate.opsForValue().get(skuId);

            // todo 修改库存操作，字段：预占库存，可售库存，占用库存 之间业务逻辑及 获取sku当前库存方法所获取的值存在疑问
            // 修改库存逻辑 ...

            if (ObjectUtils.isEmpty(inventory)) {

                return false;
            }
            return true;
        } catch (RuntimeException e) {
            // 修改为抛出自定义异常
            throw new RuntimeException("[加锁失败:" + skuId + "]");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    /**
     * 并发等待时常
     */
    private long getWaitMills() {
        return  -1;
    }

    /**
     * 业务处理max时长
     */
    private long getLeaseMills() {
        return 30 * 1000;
    }

    private void failover(String lockKey) {
        throw new RuntimeException("[加锁失败:" + lockKey + "]");
    }
}

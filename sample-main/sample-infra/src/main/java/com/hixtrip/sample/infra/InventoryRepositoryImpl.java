package com.hixtrip.sample.infra;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hixtrip.sample.domain.RedisKeyprefixConst;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Inventory;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.mapper.InventorMapper;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Redisson redisson;

    @Autowired
    private InventorMapper inventorMapper;

    public static final Integer INVENTORY_CACHE_TIMEOUT = 60 * 60 * 24;

    public static final String EMPTY_CACHE = "{}";

    //针对热点缓存 加读锁
    public static final String LOCK_INVENTORY_HOT_CACHE_PREFIX = "lock:inventory:hot_cache";

    public static final String LOCK_INVENTORY_UPDATE_PREFIX = "lock:inventory:update";

    public static Map<String, InventoryDO> inventoryDOMap = new ConcurrentHashMap<String, InventoryDO>();

    @Transactional(rollbackFor = ConcurrentModificationException.class)
    @Override
    public Integer getInventory(String skuId) {

        // 可能会出现一个问题 更新查询缓存的时候看到的数据1， 此时数据被另一个线程更新了
        //实际查询的数据和缓存数据不一致，此时通过读写锁来区别

        //目前仅实现单机的情况下，靠JVM抗并发的方法
        String inventoryCachekey = RedisKeyprefixConst.INVENTORY_CACHE+ skuId;

        InventoryDO inventoryDO = getInventoryFromCache(inventoryCachekey);
        if (inventoryDO != null) {
            //FIXME 判空逻辑需要完善
            return inventoryDO.getCurrentCnt();
        }
        RLock hotCacheLock = redisson.getLock(LOCK_INVENTORY_HOT_CACHE_PREFIX + skuId);
        //可以并发执行
        // hotCacheLock.tryLock(1, TimeUnit.SECONDS);
        hotCacheLock.lock();
        try {
            inventoryDO = getInventoryFromCache(inventoryCachekey);
            if (inventoryDO != null) {
                return inventoryDO.getCurrentCnt();
            }

            // 双重教验过后，保证了多个线程查询缓存的一致性，此时开始加读写锁保证缓存与数据库的一致性
            RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_INVENTORY_UPDATE_PREFIX + skuId);
            RLock rLock = readWriteLock.readLock();
            rLock.lock();
            try {
                inventoryDO = inventorMapper.query(skuId);
                //这个时候，可能存在一个线程过来读数据，但是缓存的数据还没从数据库加载进来
                // 为了保证读取时序的一致性，仍需要判断是否是空缓存，防止因为热点key被击穿
                if (inventoryDO!=null) {
                    redisUtil.set(inventoryCachekey, JSON.toJSONString(inventoryDO), INVENTORY_CACHE_TIMEOUT, TimeUnit.SECONDS );
                } else {
                    redisUtil.set(inventoryCachekey, EMPTY_CACHE, getEmptyCacheTimeout(), TimeUnit.SECONDS);
                }
            } catch (Exception ex) {
                //TODO 外层事务需要设置rollbackFor异常回滚， 并做事务切面拦截异常
                ex.printStackTrace();
            } finally {
                rLock.unlock();
            }
        } catch (Exception ex) {
            //TODO 外层事务需要设置rollbackFor异常回滚， 并做事务切面拦截异常
            ex.printStackTrace();
        } finally {
            hotCacheLock.unlock();
        }


        return inventoryDO.getCurrentCnt();
    }


    /**
     *
     * @param skuId
     * @param sellableQuantity    可售库存 : 运营配置的普通商品库存, 商品维度到sku
     * @param withholdingQuantity 预占库存 : 用户下单完成库存预占，仓储系统发货后释放预占库存，预占库存可以监控已下单未发货的库存量
     * @param occupiedQuantity    占用库存:  由仓储系统同步到库存系统的实物库存(下单付款后的库存,但此时因为还没收到货，所以还是占用)
     * @return
     */
    @Transactional
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        // 存在当数据量几百万的情况下,redis只能扛住10w的并发，可能导致redis挂掉(当然也可以做集群)
        // JVM + 读锁来做

        //  根据业务逻辑的话 应该是要根据订单状态查  已下单未发货， 已付款未到货两种状态
        // 先根据 orderId统计在途库存 接着按SKUID找出目前的库存量(可售库存 - 预占库存 - 占用库存)
        InventoryDO inventoryDO = null;

        RReadWriteLock readWriteLock = redisson.getReadWriteLock(LOCK_INVENTORY_UPDATE_PREFIX+ skuId);
        RLock rLock = readWriteLock.readLock();
        rLock.lock();
        try{
            inventoryDO = inventorMapper.query(skuId);
            Long currentCnt = sellableQuantity - withholdingQuantity - occupiedQuantity;
            inventoryDO.setCurrentCnt(currentCnt.intValue());
            inventorMapper.updateInventory(inventoryDO);
            redisUtil.set(RedisKeyprefixConst.INVENTORY_CACHE + skuId, JSON.toJSONString(inventoryDO), getInventoryCacheTimeout(), TimeUnit.SECONDS);
            inventoryDOMap.put(RedisKeyprefixConst.INVENTORY_CACHE + skuId, inventoryDO);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return null;
    }

    // 由于缓存可能在同一时间过期，所以采用双ttl方法 错开时间段消费，
    // 第一层TTL为空缓存，起到流量保护作用, 防止缓存击穿
    private InventoryDO getInventoryFromCache(String inventoryCachekey) {
        InventoryDO inventoryDO = inventoryDOMap.get(inventoryCachekey);
        if (inventoryDO != null) {
            return inventoryDO;
        }


        String inventoryStr = redisUtil.get(inventoryCachekey);
        if (!StringUtils.isEmpty(inventoryStr)) {
            if (EMPTY_CACHE.equals(inventoryCachekey)) {
                redisUtil.expire(inventoryCachekey,getEmptyCacheTimeout(), TimeUnit.SECONDS);
                return new InventoryDO();
            }
            inventoryDO = JSON.parseObject(inventoryStr, InventoryDO.class);
            redisUtil.expire(inventoryCachekey,getInventoryCacheTimeout(), TimeUnit.SECONDS);
        }
        return inventoryDO;
    }

    //24小时为起点，随机生成的第二个ttl
    private Integer getInventoryCacheTimeout(){
        return INVENTORY_CACHE_TIMEOUT + new Random().nextInt(5)*60*60;
    }

    //60s为起点，随机生成的第一个空保护ttl
    private Integer getEmptyCacheTimeout(){
        return  60 + new Random().nextInt(30);
    }

}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Slf4j
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private DefaultRedisScript<Long> changeScript;


    @PostConstruct
    public void init() {
        //init lua script
        changeScript = new DefaultRedisScript<>();
        changeScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/change-quantity.lua")));
        changeScript.setResultType(Long.class);
    }

    /*
        库存信息的缓存,分成两部份,一部分是库存信息,如名称、仓库等信息, 另一部分为库存数量缓存
        why: 1. 库存信息一般情况下,变化较少,信息量较多, 而数量则是频繁变化
             2. 数量变化是一个单独的业务场景, 抽离出来也有助于领域概念的实现
     */
    @Override
    public Optional<Inventory> getInventory(String skuId) {
        Object obj = redisTemplate.opsForValue().get(skuId);
        if (Objects.nonNull(obj)) {
            InventoryDO inventoryDO = (InventoryDO) obj;
            Inventory inventory = InventoryDOConvertor.INSTANCE.toInventory(inventoryDO);
            return Optional.ofNullable(inventory).map(this::decorate).map(this::assemble);
        }

        // 从数据库里取查, 放入库存信息缓存, 略
        return Optional.empty();
    }

    private Inventory assemble(Inventory inventory) {
        inventory.setRepository(this);
        return inventory;
    }

    private Inventory decorate(Inventory inv) {
        SellableInventoryCache sellableInventoryCache = this.getSellableInventoryCache(inv.getId());
        if (Objects.isNull(sellableInventoryCache)) {
            this.rebuildCache(inv);
            sellableInventoryCache = this.getSellableInventoryCache(inv.getId());
        }
        inv.setSq(sellableInventoryCache.getSq());
        inv.setWq(sellableInventoryCache.getWq());
        inv.setOq(sellableInventoryCache.getOq());
        return inv;
    }

    private SellableInventoryCache getSellableInventoryCache(String skuId) {
        List list = redisTemplate.opsForHash().multiGet(skuId, List.of("SQ", "WQ", "OQ"));
        List<String> hash = (List<String>) list;
        SellableInventoryCache cached = new SellableInventoryCache();
        if (StringUtils.hasText(hash.get(0))) {
            cached.setSq(Long.parseLong(hash.get(0)));
        }
        if (StringUtils.hasText(hash.get(1))) {
            cached.setWq(Long.parseLong(hash.get(1)));
        }
        if (StringUtils.hasText(hash.get(2))) {
            cached.setOq(Long.parseLong(hash.get(2)));
        }
        return cached;

    }

    private void rebuildCache(Inventory inv) {
        // 重建缓存, 略
    }

    /*
        核心在于 比较和扣除的操作原子化, 实现库存不会被扣成负数, 同时并发的操作在这里串行化
        1.暂考虑一个业务只会扣减一种库存的场景. 如果是多个, lua脚本中的判断前移即可
        2.根据返回值判断是否扣除成功, 可进一步细分业务执行的是扣减啥, 哪步出问题
     */
    @Override
    public boolean changeQuantity(String skuId, Long diffSq, Long diffWq, Long diffOq) {
        if (diffSq == null) {
            diffSq = 0L;
        }
        if (diffWq == null) {
            diffWq = 0L;
        }
        if (diffOq == null) {
            diffOq = 0L;
        }
        if (diffSq == 0L && diffWq == 0L && diffOq == 0L) {
            log.info("库存变化量为0,不执行更新库存缓存操作:skuId = {}", skuId);
            return true;
        }
        try {
            Long result = redisTemplate.execute(changeScript, Collections.singletonList(skuId),
                    String.valueOf(diffSq), String.valueOf(diffWq), String.valueOf(diffOq));
            return result != null && result > 0L;
        } catch (Exception e) {
            // 处理异常, 打印日志等等
            throw e;
        }
    }


    @Data
    public static class SellableInventoryCache {
        private Long sq;
        private Long wq;
        private Long oq;
    }

}

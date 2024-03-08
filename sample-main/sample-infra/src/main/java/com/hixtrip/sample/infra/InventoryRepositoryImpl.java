package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Sku;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.convertor.SkuDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.dataobject.SkuDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private void SimulationSku() {
        SkuDo skuDo = SkuDo.builder().id("1").count(100).sellableQuantity(1000L).withholdingQuantity(0L).occupiedQuantity(0L).money(BigDecimal.valueOf(100)).build();
        redisTemplate.opsForValue().set(skuDo.getId(), skuDo);
    }

    @Override
    public Integer getInventory(String skuId) {
        SkuDo skuDo = (SkuDo) redisTemplate.opsForValue().get(skuId);
        // 由于刚开始redis没有数据，所以模拟已有库存sku，该模拟skuId为1才有数据
        if (skuDo == null) {
            SimulationSku();
        skuDo = (SkuDo) redisTemplate.opsForValue().get(skuId);
        }
        try {
            return SkuDOConvertor.INSTANCE.doToDomain(skuDo).getCount();
        } catch (Exception e) {
            System.out.println("没有该库存id");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void seInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        SkuDo skuDo = SkuDo.builder()
                .id(skuId)
                .sellableQuantity(sellableQuantity)
                .withholdingQuantity(withholdingQuantity)
                .occupiedQuantity(occupiedQuantity).build();
        redisTemplate.opsForValue().set("sku", skuDo);
    }

    @Override
    public Sku getSkuInfo(String skuId) {
        SkuDo skuDo = (SkuDo) redisTemplate.opsForValue().get(skuId);
        return SkuDOConvertor.INSTANCE.doToDomain(skuDo);
    }


}

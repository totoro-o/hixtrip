package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.dto.InventoryDto;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Integer getInventory(String skuId) {
        return null;
    }

    /**
     * 避免超卖。无锁设计
     * @param inventoryDto
     * @return
     */
    @Override
    public Boolean changeInventory(InventoryDto inventoryDto) {
        //Lua 脚本 + INCRBY、DECRBY命令，将库存扣减操作使用lua脚本封装在一起，确保多个命令在一个lua命令生命周期内完成。

        return null;
    }
}

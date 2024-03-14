package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.RedisKeyprefixConst;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Inventory;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {

    @Autowired
    private InventoryRepository inventoryRepository;


    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return null;
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存 : 运营配置的普通商品库存, 商品维度到sku
     * @param withholdingQuantity 预占库存 : 用户下单完成库存预占，仓储系统发货后释放预占库存，预占库存可以监控已下单未发货的库存量
     * @param occupiedQuantity    占用库存:  由仓储系统同步到库存系统的实物库存(下单付款后的库存,但此时因为还没收到货，所以还是占用)
     * @return
     */
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        return null;
    }
}

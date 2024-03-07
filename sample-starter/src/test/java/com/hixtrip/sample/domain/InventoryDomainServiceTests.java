package com.hixtrip.sample.domain;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:17
 */
@SpringBootTest
public class InventoryDomainServiceTests {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Test
    public void getInventoryTest() {
        Integer amount = inventoryDomainService.getInventory("skuId123");
        assert amount > 0;
    }
}

package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 13:53
 */
@SpringBootTest
public class InventoryRepositoryImplTests {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void initTest() {
        inventoryRepository.init();
    }

    @Test
    public void getInventoryTest(){
        Optional<Inventory> optional = inventoryRepository.getInventory("skuId123");
        assert optional.isPresent();
    }
}

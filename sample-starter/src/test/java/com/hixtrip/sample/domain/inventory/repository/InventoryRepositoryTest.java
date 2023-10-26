package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.repository.InventoryDORepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InventoryRepositoryTest {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryDORepository inventoryDORepository;

    @BeforeEach
    void setUp() {
        InventoryDO entity = new InventoryDO();
        entity.setSkuId("1");
        entity.setSellableQuantity(100);
        entity.setOccupiedQuantity(10);
        entity.setWithholdingQuantity(10);
        inventoryDORepository.save(entity);
    }

    @Test
    void testIncrementInventoryOnField() {
        inventoryRepository.getInventory("1").ifPresent(System.out::println);
        Long result = inventoryRepository.incrementInventory("1", 0, 1, 0);
        System.out.println(result);

        Optional<Inventory> inventory = inventoryRepository.getInventory("1");
        inventory.ifPresent(System.out::println);

        assertEquals(1L, (long) result);
        inventory.ifPresent(it -> {
            assertEquals(11, it.getWithholdingQuantity());
        });
    }

    @Test
    void testIncrementManyField() {
        inventoryRepository.getInventory("1").ifPresent(System.out::println);
        Long result = inventoryRepository.incrementInventory("1", 0, -1, 1);
        System.out.println(result);

        Optional<Inventory> inventory = inventoryRepository.getInventory("1");
        inventory.ifPresent(System.out::println);

        assertEquals(1L, (long) result);
        inventory.ifPresent(it -> {
            assertEquals(100, it.getSellableQuantity());
            assertEquals(9, it.getWithholdingQuantity());
            assertEquals(11, it.getOccupiedQuantity());
        });
    }

    @Test
    void testIncrementFail() {
        Long result = inventoryRepository.incrementInventory("2", 0, 1, 0);
        assertEquals(result, 0);

        result = inventoryRepository.incrementInventory("1", -101, 1, 1);
        assertEquals(-1, result);
        inventoryRepository.getInventory("1").ifPresent(System.out::println);

        result = inventoryRepository.incrementInventory("1", 1, -11, 1);
        inventoryRepository.getInventory("1").ifPresent(System.out::println);
        assertEquals(-1, result);

        result = inventoryRepository.incrementInventory("1", 1, 1, -11);
        inventoryRepository.getInventory("1").ifPresent(System.out::println);
        assertEquals(-1, result);

        result = inventoryRepository.incrementInventory("1", 0, 0, 0);
        assertEquals(-999, result);
    }

}
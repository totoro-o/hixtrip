package com.hixtrip.sample.domain.inventory.model;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.Data;

/**
 * @CreateDate: 2023/10/24
 * @Author: ccj
 */
@Data
public class Inventory {

    private String id;

    private String name;
    // more info
    private Long sq;
    private Long wq;
    private Long oq;

    private transient InventoryRepository repository;

    // 库存扣减是 库存自身的行为(自身状态变化), 故放入领域对象内
    public boolean changeQuantity(Long diffSq, Long diffWq, Long diffOq) {
        return repository.changeQuantity(id, diffSq, diffWq, diffOq);
    }
}

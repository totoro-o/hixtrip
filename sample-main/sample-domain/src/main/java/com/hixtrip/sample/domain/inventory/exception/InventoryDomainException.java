package com.hixtrip.sample.domain.inventory.exception;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:10
 * 库存领域异常
 */
public class InventoryDomainException extends RuntimeException {

    private String message;

    public InventoryDomainException(String message) {
        super(message);
        this.message = message;
    }
}

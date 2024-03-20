package com.hixtrip.sample.infra.constant;

public interface RedisConstant {

    String SCRIPT =
            "local sellableQuantityKey = KEYS[1]\n" +
                    "local withholdingQuantityKey = KEYS[2]\n" +
                    "local occupiedQuantityKey = KEYS[3]\n" +
                    "\n" +
                    "local sellableQuantityChange = tonumber(ARGV[1])\n" +
                    "local withholdingQuantityChange = tonumber(ARGV[2])\n" +
                    "local occupiedQuantityChange = tonumber(ARGV[3])\n" +
                    "\n" +
                    "-- 获取当前值\n" +
                    "local sellableQuantity = tonumber(redis.call('GET', sellableQuantityKey)) or 0\n" +
                    "local withholdingQuantity = tonumber(redis.call('GET', withholdingQuantityKey)) or 0\n" +
                    "local occupiedQuantity = tonumber(redis.call('GET', occupiedQuantityKey)) or 0\n" +
                    "\n" +
                    "-- 计算新值\n" +
                    "sellableQuantity = sellableQuantity + sellableQuantityChange\n" +
                    "withholdingQuantity = withholdingQuantity + withholdingQuantityChange\n" +
                    "occupiedQuantity = occupiedQuantity + occupiedQuantityChange\n" +
                    "\n" +
                    "-- 更新 Redis 中的值\n" +
                    "redis.call('SET', sellableQuantityKey, sellableQuantity)\n" +
                    "redis.call('SET', withholdingQuantityKey, withholdingQuantity)\n" +
                    "redis.call('SET', occupiedQuantityKey, occupiedQuantity)\n" +
                    "\n" +
                    "return true";

    String SELLABLE_QUANTITY_KEY = "sellable:";

    String WITHHOLDING_QUANTITY_KEY = "withholding:";

    String OCCUPIED_QUANTITY_KEY = "occupied:";

    String PREFIX_KEY = "sku:inventory:";
}

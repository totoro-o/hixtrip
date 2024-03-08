package com.hixtrip.sample.domain.common.constant;

public class RedisConstants {

    // 库存剩余数量(hash结构)
    public static final String STOCK_COUNT = "SKU:STOCK_COUNT";

    // 库存量修改锁(hash结构)
    public static final String STOCK_COUNT_UPDATE_LOCK = "SKU:STOCK_COUNT_UPDATE_LOCK";
    public static String getStockCount(){
        return STOCK_COUNT;
    }
    public static String getStockCountUpdateLock(){
        return STOCK_COUNT_UPDATE_LOCK;
    }
}

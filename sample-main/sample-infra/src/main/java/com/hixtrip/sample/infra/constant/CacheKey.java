package com.hixtrip.sample.infra.constant;


public class CacheKey {

    // 缓存key 前缀
    /**
     * 库存缓存前缀  + skuId
     */
    public static String cache_stock = "cache_stock_";



    // 锁key 前缀
    /**
     * 库存锁前缀 + skuId
     */
    public static String lock_stock = "lock_stock_";

}

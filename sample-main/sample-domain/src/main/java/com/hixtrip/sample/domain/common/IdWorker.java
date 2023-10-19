package com.hixtrip.sample.domain.common;

/**
 * <p> 分布式全局ID
 *
 * @author airness
 * @since 2023/10/18 17:10
 */
public interface IdWorker {

    /**
     * 使用业务标签获取全局ID
     * @param bizTag 业务标签
     * @return 返回长整型ID
     */
    Long getLongId(String bizTag);

    /**
     * 使用业务标签获取全局ID
     * @param bizTag 业务标签
     * @return 返回字符型ID
     */
    String getStringId(String bizTag);
}

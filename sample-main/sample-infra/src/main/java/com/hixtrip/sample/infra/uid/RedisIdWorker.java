package com.hixtrip.sample.infra.uid;

import com.hixtrip.sample.domain.common.IdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p> Redis实现的全局业务ID
 *
 * @author airness
 * @since 2023/10/18 19:19
 */
@Component
@RequiredArgsConstructor
public class RedisIdWorker implements IdWorker {

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public Long getLongId(String bizTag) {
        return redisTemplate.opsForValue().increment("uuid:" + bizTag);
    }

    @Override
    public String getStringId(String bizTag) {
        return Optional.ofNullable(getLongId(bizTag)).map(Object::toString).orElseThrow(() -> new RuntimeException(bizTag + "业务Id生成失败"));
    }
}

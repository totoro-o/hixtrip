package com.hixtrip.sample.infra.script;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 * @author airness
 * @since 2023/10/18 14:39
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClasspathRedisScriptLoader {


    /**
     * 从类路径中加载脚本
     * @param classpathResource 类路径资源，例如resources下的lua，写为 lua/test-script.lua
     * @param resultType 脚本返回值类型
     * @return 返回脚本，如返回空，请检查资源是否存在
     * @param <T> 返回类型
     */
    public static <T> @Nullable DefaultRedisScript<T> loadScript(String classpathResource, Class<T> resultType) {
        Resource resource = new ClassPathResource(classpathResource);
        try {
            String script = resource.getContentAsString(StandardCharsets.UTF_8);
            return new DefaultRedisScript<>(script, resultType);
        } catch (IOException e) {
            log.warn("无法加载类路径资源：{}, 请检查是否存在该文件", classpathResource);
            return null;
        }
    }
}

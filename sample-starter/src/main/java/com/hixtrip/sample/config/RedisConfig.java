package com.hixtrip.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    /**
     * 在该方法中自定义RedisTemplate的序列化器，方法名自己取，记得加@Bean注解返回RedisTemplate对象
     *
     * @param redisConnectionFactory 连接工厂，Spring会自动注入
     * @return RedisTemplate对象
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 创建JSON序列化工具
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 设置KEY的序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置VALUE的序列化
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        // 返回
        return template;
    }

    /**
     * 原子性修改库存修改脚本
     * KEYS[1] skuId
     * ARGV[1] sellableQuantityInc 可售库存增量
     * ARGV[2] withholdingQuantityInc 预占库存增量
     * ARGV[3] occupiedQuantityInc 占用库存增量
     * <p>
     *
     * @return 返回值：
     * 0: 库存不存在
     * -1：库存不足
     * -999：库存增量都为0或有 nil 参数
     * 1：成功
     */
    @Bean
    RedisScript<Long> inventoryIncScript() {
        String script = """
                local key = KEYS[1]
                local inventory = redis.call('hgetall', key)
                if #inventory == 0 then
                    -- 库存不存在，返回0
                    return 0
                end
                                
                -- 将结果转换为hash
                local length = table.getn(inventory)
                local hash = {}
                for i = 1, length, 2 do
                  local key = inventory[i]
                  local value = inventory[i + 1]
                  hash[key] = value
                end
                                
                local sellableQuantityInc = tonumber(ARGV[1])
                local withholdingQuantityInc = tonumber(ARGV[2])
                local occupiedQuantityInc = tonumber(ARGV[3])
                                
                -- 参数校验，不能为 nil
                if sellableQuantityInc == nil or withholdingQuantityInc == nil or occupiedQuantityInc == nil then
                    return -999
                end
                                
                -- 参数校验, 不能都为0
                if sellableQuantityInc == 0 and withholdingQuantityInc == 0 and occupiedQuantityInc == 0 then
                    return -999
                end
                                
                local sellableQuantity = tonumber(hash['sellableQuantity'])
                local withholdingQuantity = tonumber(hash['withholdingQuantity'])
                local occupiedQuantity = tonumber(hash['occupiedQuantity'])
                                
                -- 库存校验，不足返回 -1
                if sellableQuantity  + sellableQuantityInc < 0 or withholdingQuantity + withholdingQuantityInc < 0  or occupiedQuantity + occupiedQuantityInc < 0  then
                    return -1
                end
                                
                -- 执行修改
                if sellableQuantityInc ~= 0 then
                    redis.call('hincrby', key, 'sellableQuantity', sellableQuantityInc)
                end
                                
                if withholdingQuantityInc ~= 0 then
                    redis.call('hincrby', key, 'withholdingQuantity', withholdingQuantityInc)
                end
                                
                if occupiedQuantityInc ~= 0 then
                    redis.call('hincrby', key, 'occupiedQuantity', occupiedQuantityInc)
                end
                return 1
                """;
        return new DefaultRedisScript<>(script, Long.class);
    }
}

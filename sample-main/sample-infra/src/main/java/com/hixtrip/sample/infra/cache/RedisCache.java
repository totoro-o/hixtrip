package com.hixtrip.sample.infra.cache;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisCache implements Cache{

    @Override
    public boolean existKey(String key) {
        return false;
    }

    @Override
    public void set(String key, Object value, long time) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        return Optional.empty();
    }
}

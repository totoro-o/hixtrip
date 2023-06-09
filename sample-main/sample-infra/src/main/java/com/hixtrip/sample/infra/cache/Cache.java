package com.hixtrip.sample.infra.cache;

import java.util.Optional;

public interface Cache {

    boolean existKey(String key);

    void set(String key, Object value, long time);

    void remove(String key);

    <T> Optional<T> get(String key, Class<T> clazz);

}

package com.hixtrip.sample.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LockRepositoryImpl {

    private Map<String,String> lockMap;

    public LockRepositoryImpl(){
        this.lockMap = new HashMap<>();
    }

    public boolean getLock(String key){
        if (lockMap.get("key") != null){
            return false;
        }else {
            lockMap.put("key","true");
            return true;
        }

    }

    public void delLock(String key){
        lockMap.remove(key);
    }
}

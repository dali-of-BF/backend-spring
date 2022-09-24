package com.fang.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author FPH
 * @since 2022年4月28日18:03:22
 */
@Component
@RequiredArgsConstructor
public class RedisCache {

    @Resource
    private  final RedisTemplate redisTemplate;

    /**
     * 通过key从redis中获取value
     * @param key
     * @return
     * @param <T>
     */
    public <T> T getCacheObject(final String key){
        ValueOperations<String,T> value = redisTemplate.opsForValue();
        return value.get(key);
    }

    /**
     * 缓存基本类型，Integer、String、实体类等
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    /**
     * 删除单个对象
     * @param key
     * @return
     */
    public Boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }
}

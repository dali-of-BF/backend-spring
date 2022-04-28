package com.fang.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 * @since 2022年4月28日18:03:22
 */
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

}

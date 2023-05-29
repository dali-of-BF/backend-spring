package com.backend.annotation.aspect;

import com.backend.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 * @since 2023年5月29日10:46:10
 * 接口限流
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimiterAspect {
    private final RedisUtils redisUtils;
//    private final RedisScript<Long> redisScript;
}

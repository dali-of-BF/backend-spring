package com.backend.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author FPH
 * @since 2023年4月20日15:01:18
 */
@Configuration
public class RedisConfig {

    /**
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public <T,V> RedisTemplate<T, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<T, V> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}

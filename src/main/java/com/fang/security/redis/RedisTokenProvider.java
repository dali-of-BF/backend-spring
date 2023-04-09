package com.fang.security.redis;

import com.fang.config.ApplicationProperties;
import com.fang.constants.SecurityRedisConstants;
import com.fang.security.DomainUserDetails;
import com.fang.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author FPH
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenProvider implements TokenProvider {

    private final RedisTemplate redisTemplate;

    private final ApplicationProperties properties;


    /**
     * @param authentication
     * @param prefix         前缀
     * @param rememberMe
     * @return
     */
    @Override
    public String createToken(Authentication authentication, String prefix, boolean rememberMe) {
        DomainUserDetails principal = (DomainUserDetails) authentication.getPrincipal();
        String token = UUID.randomUUID().toString();
        if(StringUtils.isNotBlank(prefix)){
            //需要将token前面拼接前缀
            token = prefix.concat(token);
        }
        String key = SecurityRedisConstants.AUTHORITIES_KEY.concat(token);
        redisTemplate.opsForValue().set(key,
                principal,
                rememberMe?properties.getSecurity().getExpirationTime_rememberMe()
                :properties.getSecurity().getExpirationTime_no_rememberMe(),
                TimeUnit.MILLISECONDS);
        return token;
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Authentication getAuthentication(String token) {
        DomainUserDetails userDetails = (DomainUserDetails) redisTemplate
                .opsForValue().get(SecurityRedisConstants.AUTHORITIES_KEY.concat(token));
        if (Objects.nonNull(userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }
        return null;
    }

    /**
     * @param authToken
     * @return
     */
    @Override
    public boolean validateToken(String authToken) {
        DomainUserDetails userDetails = (DomainUserDetails) redisTemplate.opsForValue()
                .get(SecurityRedisConstants.AUTHORITIES_KEY.concat(authToken));
        return Objects.nonNull(userDetails);
    }
}

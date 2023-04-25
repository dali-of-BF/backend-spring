package com.backend.security.tokenProvider;

import com.backend.config.ApplicationProperties;
import com.backend.constants.Constant;
import com.backend.constants.RedisConstants;
import com.backend.security.domain.DomainUserDetails;
import com.backend.utils.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.backend.constants.SecurityRedisConstants.AUTHORITIES_KEY;

/**
 * @author FPH
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenProvider implements TokenProvider {

    private final RedisTemplate<String,Object> redisTemplate;

    private final ApplicationProperties properties;

    @Override
    public String createToken(DomainUserDetails principal) {
        String token = UUID.randomUUID().toString();
        String key = AUTHORITIES_KEY.concat(token);
        redisTemplate.opsForValue().set(key,
                principal,
                principal.isRememberMe()?properties.getSecurity().getExpirationTime_rememberMe()
                :properties.getSecurity().getExpirationTime_no_rememberMe(),
                TimeUnit.MILLISECONDS);
        return token;
    }
    @Override
    public Authentication getAuthentication(String token) {
        DomainUserDetails userDetails = JsonMapper.covertValue(redisTemplate
                .opsForValue().get(AUTHORITIES_KEY.concat(token)),DomainUserDetails.class);
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
                .get(AUTHORITIES_KEY.concat(authToken));
        return Objects.nonNull(userDetails);
    }


    @Override
    public Boolean refreshExpiration(String token, String loginUserId,boolean rememberMe) {
        String key = AUTHORITIES_KEY.concat(token);
        //重置其时间
        redisTemplate.expire(key,rememberMe ?
                properties.getSecurity().getExpirationTime_rememberMe()
                :properties.getSecurity().getExpirationTime_no_rememberMe(), TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 移除token
     * @param token
     * @return
     */
    public Boolean removeToken(@NotNull String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        String key = RedisConstants.AUTHORITIES_KEY.concat(token);
        return redisTemplate.delete(key);
    }

    /**
     * 通过前端传来的token解析token
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(properties.getSecurity().getTokenPrefix()+ Constant.SPACE)) {
            return bearerToken.substring(properties.getSecurity().getTokenPrefix().length()+1);
        }
        return "";
    }
}

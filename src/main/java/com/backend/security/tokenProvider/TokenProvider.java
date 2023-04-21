package com.backend.security.tokenProvider;

import com.backend.security.domain.DomainUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 令牌提供接口
 * @author FPH
 * @since 2022年9月24日21:03:08
 */
@Component
public interface TokenProvider {
    /**
     * 创建token
     * @param userDetails
     * @return
     */
    String createToken(DomainUserDetails userDetails);

    /**
     * 通过token解析权限
     * @param token
     * @return
     */
    Authentication getAuthentication(String token);

    /**
     * 校验token有效性
     *
     * @param authToken
     * @return
     */
    boolean validateToken(String authToken);

    /**
     * token续期
     * @param token
     * @param loginUserId redis中的key值，通过此才能准确定位到哪条redis
     * @param rememberMe
     * @return
     */
    Boolean refreshExpiration(String token, String loginUserId,boolean rememberMe);
}

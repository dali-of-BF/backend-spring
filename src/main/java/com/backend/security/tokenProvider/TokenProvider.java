package com.backend.security.tokenProvider;

import org.springframework.security.core.Authentication;

/**
 * 令牌提供接口
 * @author FPH
 * @since 2022年9月24日21:03:08
 */
public interface TokenProvider {
    /**
     * 创建token
     * @param authentication
     * @param prefix 前缀
     * @return
     */
    String createToken(Authentication authentication,String prefix);

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

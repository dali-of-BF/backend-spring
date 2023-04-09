package com.fang.security;

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
     * @param rememberMe
     * @return
     */
    String createToken(Authentication authentication,String prefix, boolean rememberMe);

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
}

package com.fang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author FPH
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Security security = new Security();

    public Security getSecurity() {
        return security;
    }

    @Data
    public static class Security{
        /**
         * base64密钥
         */
        private String base64Secret;
        /**
         * jwt加密key
         */
        private String tokenSignKey;
        /**
         * 记住我-过期时间
         */
        private Long expirationTime_rememberMe;
        /**
         * 不记住我-过期时间
         */
        private Long expirationTime_no_rememberMe;
    }
}

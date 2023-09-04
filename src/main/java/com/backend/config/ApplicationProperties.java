package com.backend.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author FPH
 */
@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Security security = new Security();
    private final Resource resource = new Resource();
    private final Wechat wechat = new Wechat();


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
        /**
         * token前缀
         */
        private String tokenPrefix;

        /**
         * 用户注册默认密码
         */
        private String defaultPassword;
    }

    @Data
    public static class Resource{
        /**
         * 项目启动时是否初始化资源表 yes开启 no不开启
         */
        private String enableResourceInit;
    }

    @Data
    public static class Wechat{
        /**
         * 微信小程序APPID
         */
       private String appId;
        /**
         * 微信小程序密钥
         */
       private String appSecret;
    }
}

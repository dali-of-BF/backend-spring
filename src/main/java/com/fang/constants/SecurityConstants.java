package com.fang.constants;

/**
 * @author FPH
 * @since 2022年4月28日18:46:26
 */
public class SecurityConstants {
    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX="Bearer ";

    public static final String AUTHORITIES_KEY = "auth";
    /**
     * 被{@link HeaderConstant#APP_ID appId}取代
     */
    @Deprecated
    public static final String ORIGIN_CLIENT_CODE = "Origin-Client-Code";
    /**
     * appid中的wx类型
     */
    public static final String APPID_WX = "wx";




    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY="login_user_key";

}

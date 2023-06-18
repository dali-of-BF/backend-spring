package com.backend.constants;

/**
 * @author FPH
 * @since 2022年4月28日18:46:26
 */
public class SecurityConstants {

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

    public static final String ANONYMOUS_USER = "anonymousUser";
    /**
     * 当此url找不到角色时，返回此字段用作判断
     */
    public static final String ROLE_LOGIN = "ROLE_LOGIN";




    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY="login_user_key";

}

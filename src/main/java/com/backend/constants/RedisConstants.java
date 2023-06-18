package com.backend.constants;

/**
 * @author FPH
 * @since 2023年4月20日18:04:14
 *
 */
public class RedisConstants {

    public static final String AUTH = "AUTH";
    public static final String SOURCE = "SOURCE";

    /**
     * redis放菜单
     */
    public static final String AUTHORITIES_KEY= AUTH+ Constant.REDIS_BLOCK;
    public static final String SOURCE_KEY= SOURCE+ Constant.REDIS_BLOCK;
    public static final String APP_ID = HeaderConstant.APP_ID+ Constant.REDIS_BLOCK;
}

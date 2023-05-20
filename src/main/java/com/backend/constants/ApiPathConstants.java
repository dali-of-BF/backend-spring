package com.backend.constants;

/**
 * 系统api路径
 * @author FPH
 * @since 2022年8月7日14:38:32
 */
public class ApiPathConstants {
    //PC端接口
    public static final String PC="/pc";
    //公共接口
    public static final String COMMON="/common";
    public static final String SYS= PC + "/sys";

    public static final String MONITOR = COMMON + "/monitor";
    public static final String AUTH="/auth";
    public static final String SYS_ACCOUNT=SYS+"/account";
    public static final String SYS_MENU=SYS+"/menu";
    public static final String TEST="/test";

    public static final String TEST_REDIS =TEST+"/redis";
    public static final String USER_AGENT=COMMON + "/user-agent";
    public static final String RESOURCE=COMMON + "/resource";

    private ApiPathConstants(){

    }
}

package com.fang.utils.http;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author FPH
 * @since 2023年4月13日16:16:53
 */
public class IpUtils {
    /**
     * 都是静态方法，不会初始化此类，添加一个私有的构造函数
     */
    private IpUtils(){}
    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getHostName()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e)
        {
        }
        return "未知";
    }

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    public static String getHostIp()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
        }
        return "127.0.0.1";
    }
}

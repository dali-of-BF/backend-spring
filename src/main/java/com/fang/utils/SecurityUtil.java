package com.fang.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author FPH
 * springSecurity封装
 * @since 2022年6月6日17:47:54
 */
public class SecurityUtil {

    /**
     * 获取权限Authentication
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

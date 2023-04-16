package com.fang.security;

import com.fang.utils.SecurityUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author FPH
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * 判定当前请求对应的资源权限
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     * @param object the object being secured
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        String appId = SecurityUtils.getAppId();
        boolean isSuper = SecurityUtils.isSuper();

        return configAttributes;
    }

    /**
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // 所有定义的权限资源
        // 启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null
        return null;
    }

    /**
     * @param clazz the class that is being queried
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        //FilterInvocation
        return true;
    }
}

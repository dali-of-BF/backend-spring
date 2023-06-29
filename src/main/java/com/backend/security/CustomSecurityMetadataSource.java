package com.backend.security;

import com.backend.constants.SecurityConstants;
import com.backend.domain.entity.sys.SysResource;
import com.backend.service.sys.SysSourceService;
import com.backend.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author FPH
 * 动态获取url权限配置
 */
@RequiredArgsConstructor
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SysSourceService sysSourceService;
    private final HeaderUtils headerUtils;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 判定当前请求对应的资源权限
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     * @param o the object being secured
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = contextPath.concat(((FilterInvocation) o).getRequestUrl());
        Set<String> menuUrl = sysSourceService.getResource().stream().map(SysResource::getResourceUrl).collect(Collectors.toSet());
        for (String url : menuUrl) {
            if (antPathMatcher.match(url, requestUrl)) {
                //当前请求需要的权限
                Set<String> roleNames = sysSourceService.getBaseMapper().findRoleNameByMenuUrl(url,headerUtils.getAppId());
                roleNames.forEach(roleName -> {
                    SecurityConfig securityConfig = new SecurityConfig(roleName);
                    set.add(securityConfig);
                });
            }
        }
        if (ObjectUtils.isEmpty(set)) {
            return SecurityConfig.createList(SecurityConstants.ROLE_LOGIN);
        }
        return set;
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
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

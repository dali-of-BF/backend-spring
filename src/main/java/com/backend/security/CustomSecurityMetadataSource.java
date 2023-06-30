package com.backend.security;

import com.backend.domain.entity.sys.SysResource;
import com.backend.service.sys.SysSourceService;
import com.backend.utils.HeaderUtils;
import com.backend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
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
     * 当返回为空时，不走此方法{@link CustomAccessDecisionManager#decide(Authentication, Object, Collection) decide}
     * @param o the object being secured
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = contextPath.concat(((FilterInvocation) o).getRequestUrl());
        //请求方式
        String method = ((FilterInvocation) o).getHttpRequest().getMethod();
        String appId = SecurityUtils.getAppId();
        boolean isSuper = SecurityUtils.isSuper();
        Set<SysResource> resources = sysSourceService.getResource();
        if (isSuper && StringUtils.isNotEmpty(appId)) {
            //如果是超管，则返回空集合，不走decide方法
            Set<SysResource> uriSet = resources.stream().filter(i -> appId.equals(i.getAppId())).collect(Collectors.toSet());
            for (SysResource uri : uriSet) {
                if (uri.getResourceMethod().equals(method) && antPathMatcher.match(uri.getResourceUrl(), requestUrl)){
                    return SecurityConfig.createList();
                }
            }
        }
        //如果不是超管，则返回其resourceId至decide
        resources.stream().filter(resource -> {
                    String uri = resource.getResourceUrl();
                    return resource.getResourceMethod().equals(method) &&
                            antPathMatcher.match(uri, requestUrl);
                })
                .forEach(resource -> set.add(new SecurityConfig(String.valueOf(resource.getId()))));
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

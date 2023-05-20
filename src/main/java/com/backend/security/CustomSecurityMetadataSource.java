package com.backend.security;

import com.backend.domain.entity.sys.SysResource;
import com.backend.mapper.sys.SysResourceMapper;
import com.backend.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author FPH
 */
@RequiredArgsConstructor
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SysResourceMapper sysResourceMapper;

    @Value("${server.servlet.context-path}")
    private String contextPath;
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
        List<SysResource> resources = sysResourceMapper.selectList(new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getDeleted,Boolean.FALSE));

        //Object中包含用户请求request
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();
        String requestUrl = ((FilterInvocation) object).getRequest().getRequestURI();
        requestUrl = requestUrl.endsWith("/") ? requestUrl.substring(0, requestUrl.length() - 1) : requestUrl;
        String finalRequestUrl = requestUrl;
        PathMatcher pathMatcher = new AntPathMatcher();
        final String prefix = StringUtils.isNoneBlank(contextPath)
                && !"/".equals(contextPath) ? contextPath : "";
        if (isSuper && StringUtils.isNotBlank(appId)) {
            List<SysResource> superResources = sysResourceMapper.selectList(new LambdaQueryWrapper<SysResource>()
                    .eq(SysResource::getAppId,appId)
                    .eq(SysResource::getDeleted,Boolean.FALSE));
            for (SysResource resource : superResources) {
                String uri = resource.getResourceUrl();
                uri = uri.startsWith("/") ? prefix + uri : prefix + "/" + uri;
                if (resource.getResourceMethod().equals(method) &&
                        pathMatcher.match(uri, finalRequestUrl)) {
                     configAttributes.add(new SecurityConfig(resource.getId()));
                }
            }
            return configAttributes;
        }
        resources.stream().filter(resource -> {
                    String uri = resource.getResourceUrl();
                    uri = uri.startsWith("/") ? prefix + uri : prefix + "/" + uri;
                    return resource.getResourceMethod().equals(method) &&
                            pathMatcher.match(uri, finalRequestUrl);
                })
                .forEach(resource -> configAttributes.add(new SecurityConfig(resource.getId())));
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

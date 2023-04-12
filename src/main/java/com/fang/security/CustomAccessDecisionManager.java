package com.fang.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义访问决策管理器
 * 在此做出最终的访问控制决定
 * @author FPH
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * @param authentication   the caller invoking the method (not null)
     * @param object           the secured object being called
     * @param configAttributes the configuration attributes associated with the secured
     *                         object being invoked
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        configAttributes.forEach(configAttribute -> {
            //将访问所需资源与用户拥有资源进行比对
            // TODO: 2023/4/10 参考其他框架的形式，不想用sys_resource形式
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (attribute.trim().equals(authority.getAuthority())) {
                    return;
                }
            }
        });
        throw new AccessDeniedException("访问受限");
    }

    /**
     * @param attribute a configuration attribute that has been configured against the
     *                  <code>AbstractSecurityInterceptor</code>
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * @param clazz the class that is being queried
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

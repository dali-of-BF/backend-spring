package com.backend.security;

import com.backend.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
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
@Slf4j
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
        // collection 当前请求需要的权限
        log.debug("collection:{}", configAttributes);
        // 当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.debug("principal:{} authorities:{}", authentication.getPrincipal().toString(), authorities);

        for (ConfigAttribute configAttribute : configAttributes) {
            // 当前请求需要的权限
            String needRole = configAttribute.getAttribute();
            if (SecurityConstants.ROLE_LOGIN.equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else {
                    return;
                }
            }
            // 当前用户所具有的权限
            for (GrantedAuthority grantedAuthority : authorities) {
                // 包含其中一个角色即可访问
                if (grantedAuthority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
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

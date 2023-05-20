package com.backend.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author FPH
 */
public class FilterSecurityInterceptorPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

    private final AccessDecisionManager accessDecisionManager;

    private final FilterInvocationSecurityMetadataSource securityMetadataSource;

    public FilterSecurityInterceptorPostProcessor(AccessDecisionManager accessDecisionManager, FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.accessDecisionManager = accessDecisionManager;
        this.securityMetadataSource = securityMetadataSource;
    }

    @Override
    public <GenericFilterSecurityInterceptor extends FilterSecurityInterceptor> GenericFilterSecurityInterceptor postProcess(
        GenericFilterSecurityInterceptor filterSecurityInterceptor) {
        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
        return filterSecurityInterceptor;
    }
}

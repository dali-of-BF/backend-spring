package com.fang.configuration;

import com.fang.security.handler.AuthenticationEntryPointImpl;
import com.fang.security.handler.LogoutSuccessHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SpringSecurity配置类
 * @author FPH
 * @since 2022年4月28日14:56:08
 */
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    private final UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    private final AuthenticationEntryPointImpl authenticatedPrincipal;

    /**
     * 退出处理类
     */
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;
}

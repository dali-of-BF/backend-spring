package com.fang.config.security;

import com.fang.config.security.handler.AuthenticationEntryPointImpl;
import com.fang.config.security.handler.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SpringSecurity配置类
 * @author FPH
 * @since 2022年4月28日14:56:08
 */
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    private AuthenticationEntryPointImpl authenticatedPrincipal;

    /**
     * 退出处理类
     */
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
}

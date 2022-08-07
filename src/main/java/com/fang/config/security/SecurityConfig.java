package com.fang.config.security;

import com.fang.config.security.handler.AuthenticationEntryPointImpl;
import com.fang.config.security.handler.JwtAuthenticationTokenFilter;
import com.fang.config.security.handler.LogoutSuccessHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

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
    private final AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    private final CorsFilter corsFilter;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //CSRF禁用，因为不使用session
                .csrf().disable()
                //认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                //基于token，所以不需要用到session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //过滤请求
                .authorizeRequests()
                //允许对登录、注册、验证码生成进行匿名访问
                //匿名访问anonymous与无条件访问permitAll区别：无条件访问表示无论是否登录，都可以访问，匿名访问表示如果已登录，则无法访问，未登录才可以访问
                .antMatchers("/login","/register","/captchaImage").anonymous()

                .antMatchers(
                        HttpMethod.GET
                ).permitAll();


    }

    /**
     * 可通过Bean注入AuthenticationManager
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 强散列哈希加密实现
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}

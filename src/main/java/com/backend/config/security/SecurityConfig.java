package com.backend.config.security;

import com.backend.security.*;
import com.backend.security.jwt.JwtTokenProvider;
import com.backend.security.redis.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

/**
 * security配置
 * @author FPH
 * @since 2022年9月24日15:18:48
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenProvider redisTokenProvider;
    private final CorsFilter corsFilter;
    private final CustomAccessDecisionManager accessDecisionManager;
    private final CustomSecurityMetadataSource securityMetadataSource;
    private final UserDetailServiceImpl userDetailService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // AuthenticationProvider实现
    // 使用UserDetailsService和PasswordEncoder来验证用户名和密码
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //不隐藏用户找不到异常
        provider.setHideUserNotFoundExceptions(Boolean.FALSE);
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    /**
     * 处理基于用户名和密码的身份验证
     * @return
     * @throws Exception
     */
    @Bean
    public UsernamePasswordAuthenticationFilterImpl usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilterImpl filter = new UsernamePasswordAuthenticationFilterImpl();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailHandler);
        filter.setFilterProcessesUrl("/auth/login");
        filter.setPostOnly(Boolean.TRUE);
        return filter;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().
                and().logout().permitAll()
                .and().csrf().disable();//关闭CSRF保护即可。
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**")
                .antMatchers("/i18n/**")
                .antMatchers("/doc.html")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/api-docs*")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("/druid/**")
                .antMatchers("/captcha/**");
    }
}

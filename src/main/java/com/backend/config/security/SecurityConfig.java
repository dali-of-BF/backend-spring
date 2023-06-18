package com.backend.config.security;

import com.backend.config.ApplicationProperties;
import com.backend.security.*;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security配置
 * @author FPH
 * @since 2022年9月24日15:18:48
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义未登录时：返回状态码401
     */
    private final UnAuthenticationEntryPoint unAuthenticationEntryPoint;
    /**
     * 自定义登录认证
     */
    private final SelfAuthenticationProvider authenticationProvider;


    private final LoginSuccessHandle loginSuccessHandle;

    private final RedisTokenProvider redisTokenProvider;

    private final CustomAccessDecisionManager accessDecisionManager;
    private final CustomSecurityMetadataSource customSecurityMetadataSource;
    private final UserDetailServiceImpl userDetailService;

    private final ApplicationProperties properties;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //自定义登录认证
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf验证(防止跨站请求伪造攻击)
        http.csrf().disable();
        // 未登录时：返回状态码401
        http.exceptionHandling().authenticationEntryPoint(unAuthenticationEntryPoint);


    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**")
                .antMatchers("/common/**")
                .antMatchers("/i18n/**")
                .antMatchers("/doc.html")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/api-docs")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("/druid/**")
                .antMatchers("/captcha/**")
                .antMatchers("/auth/register")
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**","/**/*.ico")
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**");
    }
}

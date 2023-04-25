package com.backend.config.security;

import com.backend.config.ApplicationProperties;
import com.backend.security.*;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
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

    private final RedisTokenProvider redisTokenProvider;
    private final CorsFilter corsFilter;
    private final CustomAccessDecisionManager accessDecisionManager;
    private final CustomSecurityMetadataSource securityMetadataSource;
    private final UserDetailServiceImpl userDetailService;
    private final UnAuthenticationEntryPoint unAuthenticationEntryPoint;
    private final ApplicationProperties properties;

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

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/auth/logout")
            .logoutSuccessHandler(new LogoutSuccessHandle(redisTokenProvider)).and()
            //关闭CSRF保护
            .csrf().disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new UsernamePasswordAuthenticationFilterImpl(this.authenticationManager(),redisTokenProvider,properties),UsernamePasswordAuthenticationFilter.class)
            .addFilter(new TokenAuthenticationFilter(this.authenticationManager(),redisTokenProvider,unAuthenticationEntryPoint))
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .headers()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN).and()
            //将策略设置为禁止使用以下功能：地理位置、MIDI、同步 XHR、麦克风、相机、磁力计、陀螺仪和付款。它允许全屏模式，但只允许在同一源中使用。
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**")
                .antMatchers("/i18n/**")
                .antMatchers("/doc.html")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/api-docs*")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("/druid/**")
                .antMatchers("/captcha/**")
                .antMatchers("/auth/register");
    }
}

package com.backend.config.security;

import com.backend.security.*;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    /**
     *   JwtToken解析并生成authentication身份信息过滤器
     */
    private final TokenAuthenticationFilter authorizationTokenFilter;
    /**
     * 自定义权限不足处理器：返回状态码403
     */
    private final CustomAccessDeniedHandler accessDeniedHandler;
    /**
     * 登录成功处理类
     */
    private final LoginSuccessHandle loginSuccessHandle;

    private final RedisTokenProvider redisTokenProvider;
    /**
     * 自定义权限判断管理器
     */
    private final CustomAccessDecisionManager accessDecisionManager;
    /**
     * 动态获取url权限配置
     */
    private final CustomSecurityMetadataSource customSecurityMetadataSource;
    /**
     * 登录失败处理类
     */
    private final LoginFailHandle loginFailHandle;
    /**
     * 身份验证详细信息源
     */
//    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    private final LogoutSuccessHandle logoutSuccessHandle;

    private final CustomAuthenticationDetailsSource customAuthenticationDetailsSource;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonAuthenticationFilter authenticationFilter() throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(loginSuccessHandle);
        filter.setAuthenticationFailureHandler(loginFailHandle);
        filter.setAuthenticationDetailsSource(customAuthenticationDetailsSource);
        filter.setFilterProcessesUrl("/auth/login");
        filter.setPostOnly(true);
        return filter;
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

        // JwtToken解析并生成authentication身份信息过滤器
        http.addFilterBefore(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 无权访问时：返回状态码403
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        // url权限认证处理
        http.antMatcher("/**").authorizeRequests()
                //.antMatchers("/security/user/**").hasRole("ADMIN") //需要ADMIN角色才可以访问
                //.antMatchers("/connect").hasIpAddress("127.0.0.1") //只有ip[127.0.0.1]可以访问'/connect'接口
                .anyRequest() //其他任何请求
                .authenticated() //都需要身份认证
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(customSecurityMetadataSource); //动态获取url权限配置
                        o.setAccessDecisionManager(accessDecisionManager); //权限判断
                        return o;
                    }
                });

        // 开启自动配置的登录功能
        http.formLogin() //开启登录
                .loginProcessingUrl("/auth/login") //自定义登录请求路径(post)
                //自定义登录用户名密码属性名,默认为username和password
                .usernameParameter("username").passwordParameter("password")
                //验证成功处理器(前后端分离)：生成token及响应状态码200
                .successHandler(loginSuccessHandle)
                //验证失败处理器(前后端分离)：返回状态码402
                .failureHandler(loginFailHandle)
                .authenticationDetailsSource(customAuthenticationDetailsSource); //身份验证详细信息源(登录验证中增加额外字段)

        // 将session策略设置为无状态的,通过token进行登录认证
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 开启自动配置的注销功能
        http.logout() //用户注销, 清空session
                //自定义注销请求路径
                .logoutUrl("/auth/logout")
                //注销成功处理器(前后端分离)：返回状态码200
                .logoutSuccessHandler(logoutSuccessHandle);

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

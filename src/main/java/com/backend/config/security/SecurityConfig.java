package com.backend.config.security;

import com.backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    /**
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("backend")
//                .password(passwordEncoder().encode("123456"))
//                .authorities("/*");
//    }

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

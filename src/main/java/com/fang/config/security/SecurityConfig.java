package com.fang.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * security配置
 * @author FPH
 * @since 2022年9月24日15:18:48
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().
                and().logout().permitAll()
                .and().csrf().disable();//关闭CSRF保护即可。
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
       return new BCryptPasswordEncoder();
    }
}

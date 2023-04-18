package com.backend.security;

import com.backend.security.jwt.JwtTokenProvider;
import com.backend.security.redis.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author FPH
 */
@RequiredArgsConstructor
public class TokenAuthenticationConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenProvider redisTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * @param builder
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        TokenAuthenticationFilter customFilter = new TokenAuthenticationFilter(jwtTokenProvider, redisTokenProvider,authenticationEntryPoint);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

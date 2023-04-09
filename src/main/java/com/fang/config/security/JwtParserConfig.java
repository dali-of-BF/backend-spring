package com.fang.config.security;

import com.fang.config.ApplicationProperties;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FPH
 */
@Configuration
@RequiredArgsConstructor
public class JwtParserConfig {

    private final ApplicationProperties properties;
    @Bean
    public JwtParser jwtParser(){
        return Jwts.parserBuilder()
                .setSigningKey(properties.getSecurity().getTokenSignKey())
                .build();
    }
}

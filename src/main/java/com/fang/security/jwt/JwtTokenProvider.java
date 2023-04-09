package com.fang.security.jwt;

import com.fang.config.ApplicationProperties;
import com.fang.constants.SecurityConstants;
import com.fang.security.DomainUserDetails;
import com.fang.security.TokenProvider;
import com.google.common.collect.Lists;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author FPH
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final ApplicationProperties properties;

    private final JwtParser jwtParser;


    @Override
    public String createToken(Authentication authentication,String prefix, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity=new Date(rememberMe?now+properties.getSecurity().getExpirationTime_rememberMe()
                :now+properties.getSecurity().getExpirationTime_no_rememberMe());
        DomainUserDetails principal = (DomainUserDetails)authentication.getPrincipal();

        return Jwts
                .builder()
                //头
                .setHeaderParam(JwsHeader.ALGORITHM,SignatureAlgorithm.HS512.getValue())
                .setHeaderParam(Header.TYPE,"JWT")
                //载荷：默认信息
                .setSubject(principal.getNickname())
                //载荷 自定义信息
                .claim("current",principal.getCurrent())
                .claim("nickname",principal.getNickname())
                .claim("username",principal.getUsername())
                .claim("phone",principal.getPhone())
                .claim(SecurityConstants.AUTHORITIES_KEY,authorities)
                //签发时间
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(properties.getSecurity().getBase64Secret().getBytes()), SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Lists.newArrayList();
        if(Objects.isNull(claims.get(SecurityConstants.AUTHORITIES_KEY))){
            authorities = Arrays
                    .stream(claims.get(SecurityConstants.AUTHORITIES_KEY).toString().split(","))
                    .filter(auth -> !auth.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        // TODO: 2023/4/9 当且仅当微信登录时才需要使用jwt获取用户信息，
        //  否则一般推荐redis的方式获取，后期若引入的oauth2,可能将会使其更优化
        return  null;
    }

    /**
     * @param authToken
     * @return
     */
    @Override
    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}

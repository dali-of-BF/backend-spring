package com.fang.security.jwt;

import com.fang.config.ApplicationProperties;
import com.fang.security.DomainUserDetails;
import com.fang.security.TokenProvider;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author FPH
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final ApplicationProperties properties;


    @Override
    public String createToken(Authentication authentication,String prefix, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (Boolean.TRUE.equals(rememberMe)){
            //两小时过期
            validity= new Date(now+2*60*60*1000);
        }else {
            validity= new Date(now+2*60*60*10000000);
        }
        DomainUserDetails principal = (DomainUserDetails)authentication.getPrincipal();

        return Jwts
                .builder()
                //头
                .setHeaderParam(JwsHeader.ALGORITHM,"HS256")
                .setHeaderParam(Header.TYPE,"JWT")
                //载荷 自定义信息
                .claim("nickname",principal.getNickname())
                .claim("username",principal.getUsername())
                // TODO: 2023/3/26 后续可继续引入新的信息
                //载荷：默认信息
                .setSubject(principal.getNickname())
                //签发时间
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,properties.getSecurity().getTokenSignKey())
                .setExpiration(validity)
                .compact();
    }

    /**
     * @param token
     * @param clientCode
     * @return
     */
    @Override
    public Authentication getAuthentication(String token, String clientCode) {
        return null;
    }
}

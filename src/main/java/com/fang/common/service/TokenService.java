package com.fang.common.service;

import com.fang.common.Constants;
import com.fang.common.redis.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token验证处理
 * @author FPH
 * @since 2022年4月28日17:56:38
 */
@Component
public class TokenService {
    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;
    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;
    /**
     * 令牌有效期（默认三十分钟）
     */
    @Value("${token.expireTime}")
    private Integer expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request){
        //获取header中的token
        String token = request.getHeader(this.header);
        if(token!=""&&token.startsWith(Constants.TOKEN_PREFIX)){
            //去除前缀
            token=token.replace(Constants.TOKEN_PREFIX,"");
        }
        return token;
    }

    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY+uuid;
    }

    /**
     * 从令牌中获取数据声明
     * @param token
     * @return
     */
    private Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从数据声明中生成令牌
     * @param claims
     * @return
     */
    private String createToken(Map<String,Object> claims){
        String token=Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
        return token;
    }

}

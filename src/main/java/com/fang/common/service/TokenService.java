package com.fang.common.service;

import com.alibaba.druid.util.StringUtils;
import com.fang.common.Constants;
import com.fang.common.redis.RedisCache;
import com.fang.exception.GlobalException;
import com.fang.pojo.model.LoginUser;
import com.fang.utils.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisCache redisCache;

    /**
     * 获取header中的token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request){
        String token = request.getHeader(this.header);
        if(token != "" && token.startsWith(Constants.TOKEN_PREFIX)){
            //去除前缀
            token = token.replace(Constants.TOKEN_PREFIX,"");
        }
        return token;
    }

    /**
     * 从令牌中获取数据声明
     * @param token
     * @return
     */
    public Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 刷新令牌有效期
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime()+expireTime*MILLIS_MINUTE);
        //根据UUID将loginUser缓存
        String loginKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(loginKey,loginUser,expireTime, TimeUnit.MINUTES);
    }


    /**
     * 从数据声明中生成令牌
     * @param claims
     * @return
     */
    public String createToken(Map<String,Object> claims){
        String token=Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
        return token;
    }

    public String createToken(LoginUser loginUser){
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY,token);
        return createToken(claims);
    }

    /**
     * 删除用户身份信息
     * @param token
     */
    public void delUserLogin(String token){
        if(Boolean.FALSE.equals(StringUtils.isEmpty(token))){
            String loginKey = getTokenKey(token);
            redisCache.deleteObject(loginKey);
        }
    }

    /**
     * 获取前缀
     * @param uuid
     * @return
     */
    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 获取登录用户信息
     * @param request 获取请求中的请求头
     * @return
     */
    public LoginUser getLoginUser(HttpServletRequest request){
        //获取请求令牌
        String token = getToken(request);
        if(Boolean.FALSE.equals(StringUtils.isEmpty(token))){
            throw new GlobalException("未获取到token");
        }
        Claims claims = parseToken(token);
        String uuid = (String)claims.get(Constants.LOGIN_USER_KEY);
        String tokenKey = getTokenKey(uuid);
        LoginUser user = redisCache.getCacheObject(tokenKey);
        return user;
    }

}

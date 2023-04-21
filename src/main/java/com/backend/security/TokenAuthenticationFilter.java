package com.backend.security;

import com.backend.common.result.ResponseUtils;
import com.backend.constants.HeaderConstant;
import com.backend.security.domain.DomainUserDetails;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author FPH
 * 如果找到与有效用户对应的标头，则过滤传入请求并安装 Spring Security 主体。
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private final  RedisTokenProvider redisTokenProvider;

    private final   UnAuthenticationEntryPoint authenticationEntryPoint;



    public TokenAuthenticationFilter(AuthenticationManager authenticationManager,RedisTokenProvider redisTokenProvider,UnAuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager);
        this.redisTokenProvider=redisTokenProvider;
        this.authenticationEntryPoint=authenticationEntryPoint;
    }

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientCode = request.getHeader(HeaderConstant.APP_ID);
        if(StringUtils.isBlank(clientCode)){
            ResponseUtils.error(response,HeaderConstant.APP_ID+" not find");
            return;
        }
        String token = redisTokenProvider.resolveToken(request);
        if(StringUtils.isBlank(token)){
            ResponseUtils.error(response,"无法解析token，请联系管理员");
            return;
        }
        try {
            Authentication authentication = redisTokenProvider.getAuthentication(token);
            if(Objects.nonNull(authentication)){
                DomainUserDetails principal = (DomainUserDetails) authentication.getPrincipal();
                //更新一下token的过期时间
                redisTokenProvider.refreshExpiration(token,principal.getCurrent(),principal.isRememberMe());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (AuthenticationException e){
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

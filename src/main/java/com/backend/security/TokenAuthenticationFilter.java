package com.backend.security;

import com.backend.config.ApplicationProperties;
import com.backend.security.domain.DomainUserDetails;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author FPH
 * 如果找到与有效用户对应的标头，则过滤传入请求并安装 Spring Security 主体。
 * 每一次的请求都会走这里
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final RedisTokenProvider redisTokenProvider;

    private final UnAuthenticationEntryPoint authenticationEntryPoint;
    private final ApplicationProperties applicationProperties;

    /**
     * 解析token并生成authentication身份信息
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = redisTokenProvider.resolveToken(request);
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
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
            log.error("JwtToken validity!! error={}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

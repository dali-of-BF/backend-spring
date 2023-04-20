package com.backend.security;

import com.backend.config.ApplicationProperties;
import com.backend.constants.HeaderConstant;
import com.backend.exception.BusinessException;
import com.backend.security.domain.DomainUserDetails;
import com.backend.security.tokenProvider.JwtTokenProvider;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
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
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private  ApplicationProperties applicationProperties;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenProvider redisTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 这里的构造函数需要public，不仅是注入这三个bean，也是要当做构造函数使用
     * 故不使用@RequiredArgsConstructor注解
     * @param jwtTokenProvider
     * @param redisTokenProvider
     * @param authenticationEntryPoint
     */
    public TokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                     RedisTokenProvider redisTokenProvider,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTokenProvider = redisTokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
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
            throw new BusinessException(HeaderConstant.APP_ID+" not find");
        }
        String token = redisTokenProvider.resolveToken(request);
        if(StringUtils.isBlank(token)){
            throw new BusinessException("无法解析token，请联系管理员");
        }
        try {
            Authentication authentication = redisTokenProvider.getAuthentication(token);
            if(Objects.nonNull(authentication)){
                DomainUserDetails principal = (DomainUserDetails) authentication.getPrincipal();
                redisTokenProvider.refreshExpiration(token,principal.getCurrent(),principal.isRememberMe());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (AuthenticationException e){
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

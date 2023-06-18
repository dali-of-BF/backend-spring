package com.backend.security;

import com.backend.common.result.ResponseUtils;
import com.backend.config.ApplicationProperties;
import com.backend.security.domain.DomainUserDetails;
import com.backend.security.domain.LoginVO;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author FPH
 * 登录成功处理类
 */
@RequiredArgsConstructor
@Component
public class LoginSuccessHandle implements AuthenticationSuccessHandler {
    private final RedisTokenProvider redisTokenProvider;
    private final ApplicationProperties properties;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        DomainUserDetails userDetails = (DomainUserDetails) authentication.getPrincipal();
        String token = redisTokenProvider.createToken(userDetails);
        LoginVO loginVO = LoginVO.builder()
                .accessToken(token)
                .nickname(userDetails.getNickname())
                .gender(userDetails.getGender())
                .rememberMe(userDetails.isRememberMe())
                .avatar(userDetails.getAvatar())
                .phone(userDetails.getPhone())
                .superAdmin(userDetails.isSuperAdmin())
                .authorities(userDetails.getAuthorities())
                .tokenPrefix(properties.getSecurity().getTokenPrefix())
                .build();
        ResponseUtils.success(response,loginVO);
    }
}

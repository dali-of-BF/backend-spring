package com.backend.security;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import com.backend.security.domain.LoginVO;
import com.backend.security.redis.RedisTokenProvider;
import com.backend.utils.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * 登录成功处理类
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTokenProvider redisTokenProvider;
    /**
     * 登录成功后将一系列参数信息返回给前端
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result<LoginVO> result = new Result<>();
        DomainUserDetails userDetails = (DomainUserDetails) authentication.getPrincipal();
        String token = redisTokenProvider.createToken(authentication, null, false);
        LoginVO loginVO = LoginVO.builder()
                .accessToken(token)
                .nickname(userDetails.getNickname())
                .gender(userDetails.getGender())
                .firstTimeLogin(userDetails.isFirstTimeLogin())
                .avatar(userDetails.getAvatar())
                .phone(userDetails.getPhone())
                .superAdmin(userDetails.isSuperAdmin())
                .build();
        result.setCode(HttpStatus.SUCCESS);
        result.setData(loginVO);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.SUCCESS);
        try {
            httpServletResponse.getWriter().append(JsonMapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new BadCredentialsException("LoginSuccessHandler ERROR \n Failed to decode basic authentication token");
        }

    }
}

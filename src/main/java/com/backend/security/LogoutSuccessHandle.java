package com.backend.security;

import com.backend.annotation.Log;
import com.backend.common.result.ResponseUtils;
import com.backend.security.tokenProvider.RedisTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2023年4月19日11:10:28
 * 成功退出登录处理类
 */
@Component
@RequiredArgsConstructor
public class LogoutSuccessHandle implements LogoutSuccessHandler {

    private final RedisTokenProvider redisTokenProvider;

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * onLogoutSuccess 方法实际上是在实现 LogoutSuccessHandler 接口的回调方法，而不是一个被切入的方法。
     * 切面是用于拦截和处理方法调用的，而不是接口回调方法。因此，无法直接在接口回调方法上应用切面。
     */
    @Log
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        redisTokenProvider.removeToken(redisTokenProvider.resolveToken(request));
        ResponseUtils.success(response,"退出成功");
    }
}

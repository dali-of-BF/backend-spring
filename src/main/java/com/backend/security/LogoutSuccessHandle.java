package com.backend.security;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import com.backend.security.tokenProvider.RedisTokenProvider;
import com.backend.utils.JsonMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2023年4月19日11:10:28
 * 成功退出登录处理类
 */
public class LogoutSuccessHandle implements LogoutSuccessHandler {

    private final RedisTokenProvider redisTokenProvider;

    public LogoutSuccessHandle(RedisTokenProvider redisTokenProvider) {
        this.redisTokenProvider = redisTokenProvider;
    }

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        redisTokenProvider.removeToken(redisTokenProvider.resolveToken(request));
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.SUCCESS);
        result.setData("退出成功");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.SUCCESS);
        try {
            response.getWriter().append(JsonMapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new BadCredentialsException("Logout ERROR \n Failed to decode basic authentication token");
        }
    }
}

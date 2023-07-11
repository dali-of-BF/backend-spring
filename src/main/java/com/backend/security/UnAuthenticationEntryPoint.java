package com.backend.security;

import com.backend.common.result.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2023年4月21日14:42:26
 *
 */
@Component
public class UnAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {
    /**
     * 自定义未登录时：返回状态码401
     *
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtils.unauthorized(response,"登录已过期，请重新登录！","登录已过期，请重新登录！");
    }

    /**
     * 自定义拒绝访问处理程序
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e){
        ResponseUtils.forbidden(httpServletResponse,"权限不足","权限不足");
    }
}

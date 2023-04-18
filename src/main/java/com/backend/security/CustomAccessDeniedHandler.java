package com.backend.security;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import com.backend.utils.JsonMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * 自定义拒绝访问处理程序
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.FORBIDDEN);
        result.setMessage(e.getMessage());
        result.getError().setCode(HttpStatus.FORBIDDEN);
        result.getError().setMessage(e.getMessage());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().append(JsonMapper.writeValueAsString(result));
    }
}

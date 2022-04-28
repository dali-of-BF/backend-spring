package com.fang.security.handler;

import com.alibaba.fastjson.JSON;
import com.fang.common.AjaxResult;
import com.fang.common.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2022年4月28日16:30:37
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Integer code= HttpStatus.UNAUTHORIZED;
        String msg="请求访问："+httpServletRequest.getRequestURI()+"，认证失败，无法访问系统资源";
        String string= JSON.toJSONString(AjaxResult.error(code,msg));
        httpServletResponse.setStatus(HttpStatus.SUCCESS);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().print(string);
    }
}

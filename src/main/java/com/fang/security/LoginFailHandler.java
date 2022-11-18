package com.fang.security;

import com.fang.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 * @author FPH
 * @since 2022年10月31日15:02:30
 */
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result<Object> result = new Result<>();
        String msg="您输入的账号密码不正确";
        // TODO: 2022/10/31 异常判断，通过异常类选择返回的msg消息是什么
        result.setCode(Result.UNAUTHORIZED);
        result.setMsg(msg);
        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().append(objectMapper.writeValueAsString(result));
    }
}

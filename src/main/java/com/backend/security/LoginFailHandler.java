package com.backend.security;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import com.backend.utils.JsonMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 * @author FPH
 * @since 2022年10月31日15:02:30
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Result<Object> result = new Result<>();
        String msg = "账号或密码错误，请重新输入";
        if (e instanceof DisabledException) {
            msg = "您输入的账号已停用！";
        } else if (e instanceof UsernameNotFoundException) {
            msg = "您输入的账号不存在，请重新输入！";
        } else if (e instanceof InternalAuthenticationServiceException) {
            msg = e.getMessage();
        }
        result.setCode(HttpStatus.UNAUTHORIZED);
        result.getError().setCode(HttpStatus.UNAUTHORIZED);
        result.getError().setMessage(msg);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().append(JsonMapper.writeValueAsString(result));
    }
}

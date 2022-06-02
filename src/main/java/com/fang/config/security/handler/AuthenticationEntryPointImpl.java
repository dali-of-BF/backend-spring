package com.fang.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.fang.common.AjaxResult;
import com.fang.common.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2022年4月28日16:30:37
 * 处理匿名用户访问无权限资源时的异常
 * 当程序出现异常错误500时，会进入到commence方法中
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse  response, AuthenticationException e) throws IOException {

        Integer code= HttpStatus.UNAUTHORIZED;
        String msg="请求访问："+httpServletRequest.getRequestURI()+"，认证失败，无法访问系统资源";

        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");

        String string= JSON.toJSONString(AjaxResult.error(code,msg));
        response.setStatus(HttpStatus.SUCCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(string);
    }
}

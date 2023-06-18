package com.backend.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author FPH
 * 自定义web身份验证详细信息(用于登录验证中增加额外参数)
 */
@EqualsAndHashCode(callSuper = false)
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails implements Serializable {

    private final String macAddress;
    private final Boolean rememberMe;

    CustomWebAuthenticationDetails(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
//        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String s = headerNames.nextElement();
//            String header = httpServletRequest.getHeader(s);
//            System.out.println(s + ": " + header);
//        }
        macAddress = httpServletRequest.getParameter("macAddress");
        rememberMe = Boolean.valueOf(httpServletRequest.getParameter("rememberMe"));
    }

    String getMacAddress() {
        return macAddress;
    }
    Boolean getRememberMe() {
        return rememberMe;
    }
}

package com.backend.security;

import com.backend.exception.BusinessException;
import com.backend.utils.JsonMapper;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FPH
 * 自定义web身份验证详细信息(用于登录验证中增加额外参数)
 */
@EqualsAndHashCode(callSuper = false)
@Slf4j
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
        Map<String, Object> authenticationBean = new HashMap<>();
        try {
            authenticationBean = JsonMapper.readValue(httpServletRequest.getInputStream(), Map.class);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        macAddress = (String) authenticationBean.get("macAddress");
        rememberMe = (Boolean) authenticationBean.get("rememberMe");
    }

    String getMacAddress() {
        return macAddress;
    }
    Boolean getRememberMe() {
        return rememberMe;
    }
}

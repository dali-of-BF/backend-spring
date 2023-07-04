package com.backend.security;

import com.backend.constants.HeaderConstant;
import com.backend.utils.BodyReaderHttpServletRequestWrapper;
import com.backend.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author FPH
 * @since 2023年6月27日17:32:49
 * 将表单登录转为json格式
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 项目启动时调用：设置登录请求地址
     */
    public JsonAuthenticationFilter() {
    }

    /**
     * 调用登录接口时调用该方法
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String clientCode = request.getHeader(HeaderConstant.APP_ID);
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (StringUtils.isBlank(clientCode)) {
            throw new AuthenticationServiceException("APP_ID can not be null");
        }
        try {
            BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            Map<String, String> authenticationBean = JsonMapper.readValue(requestWrapper.getInputStream(), Map.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY), authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY));
            this.setDetails(requestWrapper, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }
}

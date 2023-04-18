package com.fang.security;

import com.fang.constants.Constant;
import com.fang.constants.HeaderConstant;
import com.fang.exception.BusinessException;
import com.fang.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 发起登录请求后的过滤与处理
 * @author FPH
 */
public class UsernamePasswordAuthenticationFilterImpl extends UsernamePasswordAuthenticationFilter {

    /**
     * 调用登录接口是调用该方法
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String appId = request.getHeader(HeaderConstant.APP_ID);
        if (StringUtils.isNotBlank(appId)){
            throw new BusinessException("appid can not be null");
        }
        //判断是否post请求
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //判断ContentType类型
        if(MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())){
            try {
                Map<String,String> authenticationBean = JsonMapper.readValue(request.getInputStream(), Map.class);
                String usernameAndOrigin = String.format("%s%s%s", authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY),
                        Constant.DASH, appId);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usernameAndOrigin, authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY));
                this.setDetails(request,authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }catch (Exception e){
                throw new AuthenticationServiceException("UsernamePasswordAuthenticationFilterImpl ERROR");
            }
        }else {
            throw new AuthenticationServiceException("Authentication 'Content-Type' not supported");
        }
    }
}

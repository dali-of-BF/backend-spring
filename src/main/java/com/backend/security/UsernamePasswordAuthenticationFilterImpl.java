package com.backend.security;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import com.backend.config.ApplicationProperties;
import com.backend.constants.HeaderConstant;
import com.backend.security.domain.DomainUserDetails;
import com.backend.security.domain.LoginDTO;
import com.backend.security.domain.LoginVO;
import com.backend.security.tokenProvider.RedisTokenProvider;
import com.backend.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 发起登录请求后的过滤与处理
 * @author FPH
 */
public class UsernamePasswordAuthenticationFilterImpl extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final RedisTokenProvider redisTokenProvider;
    private final ApplicationProperties properties;

    public UsernamePasswordAuthenticationFilterImpl(AuthenticationManager authenticationManager, RedisTokenProvider redisTokenProvider,
                                                    ApplicationProperties properties) {
        this.redisTokenProvider=redisTokenProvider;
        this.authenticationManager=authenticationManager;
        this.properties=properties;
        this.setFilterProcessesUrl("/auth/login");
        this.setPostOnly(Boolean.TRUE);
    }

    /**
     * 调用登录接口是调用该方法
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String appId = request.getHeader(HeaderConstant.APP_ID);
        if (StringUtils.isBlank(appId)){
            throw new AuthenticationServiceException(HeaderConstant.APP_ID+" can not be null");
        }
        //判断是否post请求
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //判断ContentType类型
        if(MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())){
            try {
                LoginDTO loginDTO = JsonMapper.readValue(request.getInputStream(), LoginDTO.class);
                String usernameAndRememberMe = String.format("%s%s%s", loginDTO.getUsername(),
                        "-", loginDTO.getRememberMe());
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usernameAndRememberMe,
                        loginDTO.getPassword());
                setDetails(request,authRequest);
                return authenticationManager.authenticate(authRequest);
            }catch (Exception e){
                throw new AuthenticationServiceException("UsernamePasswordAuthenticationFilterImpl ERROR");
            }
        }else {
            throw new AuthenticationServiceException("Authentication 'Content-Type' not supported");
        }
    }

    /**
     * 登录成功
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Result<LoginVO> result = new Result<>();
        DomainUserDetails userDetails = (DomainUserDetails) authResult.getPrincipal();
        String token = redisTokenProvider.createToken(userDetails);
        LoginVO loginVO = LoginVO.builder()
                .accessToken(token)
                .nickname(userDetails.getNickname())
                .gender(userDetails.getGender())
                .rememberMe(userDetails.isRememberMe())
                .avatar(userDetails.getAvatar())
                .phone(userDetails.getPhone())
                .superAdmin(userDetails.isSuperAdmin())
                .tokenPrefix(properties.getSecurity().getTokenPrefix())
                .build();
        result.setCode(HttpStatus.SUCCESS);
        result.setData(loginVO);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.SUCCESS);
        try {
            response.getWriter().append(JsonMapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new BadCredentialsException("LoginSuccessHandler ERROR \n Failed to decode basic authentication token");
        }
    }

    /**
     * 登录失败处理类
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        Result<Object> result = new Result<>();
        String msg = "账号或密码错误，请重新输入";
        if (failed instanceof DisabledException) {
            msg = "您输入的账号已停用！";
        } else if (failed instanceof UsernameNotFoundException) {
            msg = "您输入的账号不存在，请重新输入！";
        } else if (failed instanceof InternalAuthenticationServiceException) {
            msg = failed.getMessage();
        }
        result.setCode(HttpStatus.UNAUTHORIZED);
        result.getError().setCode(HttpStatus.UNAUTHORIZED);
        result.getError().setMessage(msg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(JsonMapper.writeValueAsString(result));
    }
}

package com.backend.security;

import com.backend.security.domain.DomainUserDetails;
import com.backend.utils.SpringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SelfAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailServiceImpl userDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取身份验证详细信息
        CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
        //表单输入的用户名
        String username = (String) authentication.getPrincipal();
        //表单输入的密码
        String password = (String) authentication.getCredentials();

        DomainUserDetails userInfo = userDetailService.loadUserByUsername(username);
        userInfo.setRememberMe(customWebAuthenticationDetails.getRememberMe());
        //校验用户名密码
        boolean matches = SpringUtils.getBean(PasswordEncoder.class).matches(password, userInfo.getPassword());
        if (Boolean.FALSE.equals(matches)) {
            throw new BadCredentialsException("密码错误");
        }
        return new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(),userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

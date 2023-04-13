package com.fang.utils;

import com.fang.constants.SecurityConstants;
import com.fang.security.DomainUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

/**
 * @author FPH
 * Security帮助类
 */
@Slf4j
@NoArgsConstructor
public class SecurityUtils {
    private static String extractPrincipal(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static Optional<String> getCurrentUserLogin(){
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(context.getAuthentication()));
    }

    public static String getSystemCode(){
        String systemCode = null;
        Optional<DomainUserDetails> currentUserDetails = getCurrentUserDetails();
        if (Objects.nonNull(currentUserDetails) && currentUserDetails.isPresent()) {
            systemCode = currentUserDetails.get().getSystemCode();
        }
        return systemCode;
    }

    public static Optional<DomainUserDetails> getCurrentUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        DomainUserDetails userDetails = null;
        if (ObjectUtils.isNotEmpty(authentication)) {
            if (authentication.getDetails() instanceof UserDetails) {
                return Optional.of((DomainUserDetails) authentication.getDetails());
            } else if (!SecurityConstants.ANONYMOUS_USER.equals(authentication.getPrincipal())) {
                try {
                    String json = JsonMapper.writeValueAsString(authentication.getPrincipal());
                    userDetails = JsonMapper.readValue(json, DomainUserDetails.class);
                } catch (JsonProcessingException e) {
                    log.error("序列化/反序列化异常===", e);
                }
                return Optional.ofNullable(userDetails);
            }
        }
        return Optional.ofNullable(userDetails);
    }


}

package com.backend.utils;

import com.backend.constants.SecurityConstants;
import com.backend.security.domain.DomainUserDetails;
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
        return Optional.ofNullable(extractPrincipal(getAuthentication()));
    }

    /**
     * 获取系统code，例如wx等
     * @return
     */
    public static String getAppId(){
        Optional<DomainUserDetails> currentUserDetails = getCurrentUserDetails();
        if (Objects.nonNull(currentUserDetails) && currentUserDetails.isPresent()) {
            return currentUserDetails.get().getAppId();
        }
        return null;
    }

    /**
     * 是否超管
     * @return
     */
    public static boolean isSuper(){
        boolean isSuper = false;
        Optional<DomainUserDetails> currentUserDetails = getCurrentUserDetails();
        if (Objects.nonNull(currentUserDetails) && currentUserDetails.isPresent()) {
            isSuper = currentUserDetails.get().isSuperAdmin();
        }
        return isSuper;
    }

    public static Optional<DomainUserDetails> getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
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

    /**
     * 获取Authentication
     * @return
     */
    public static Authentication getAuthentication(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

}

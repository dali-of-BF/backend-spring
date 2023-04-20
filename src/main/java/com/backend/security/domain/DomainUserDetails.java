package com.backend.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author FPH
 */
@Getter
@AllArgsConstructor
public class DomainUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 2928965590106589921L;
    /**
     * 用户ID
     */
    private String current;
    private String username;
    private String nickname;
    /**
     * {@link JsonIgnore 返回的json数据即不包含该属性}
     */
    @JsonIgnore
    private String password;
    /**
     * 账号是否未过期
     */
    @Builder.Default
    private boolean isAccountNonExpired = true;

    /**
     * 账户是否未锁定
     */
    @Builder.Default
    private boolean isAccountNonLocked = true;

    /**
     * 凭据是否未过期
     */
    @Builder.Default
    private boolean isCredentialsNonExpired=true;

    /**
     * 是否开启账号
     */
    @Builder.Default
    private boolean isEnabled=true;

    @Setter
    private String token;

    private String gender;

    private String avatar;
    private String phone;

    /**
     * 是否超管
     */
    private boolean superAdmin;

    /**
     * 是否首次登录
     */
    private boolean firstTimeLogin;
    /**
     * 是否记住密码
     */
    private boolean rememberMe;
    /**
     * 所属系统
     */
    private String appId;

    /**
     * 权限
     */
    @Setter
    private Collection<? extends GrantedAuthority> authorities;

}

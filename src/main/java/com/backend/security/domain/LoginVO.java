package com.backend.security.domain;

import com.backend.config.security.GrantedAuthorityDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author FPH
 */
@Data
@Builder
public class LoginVO {
    @ApiModelProperty(value = "访问令牌")
    private String accessToken;
    @ApiModelProperty(value = "用户名")
    private String nickname;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "性别：1-男;0-女")
    private String gender;
    @ApiModelProperty(value = "是否超管")
    private Boolean superAdmin;

    @ApiModelProperty(value = "记住我")
    private Boolean rememberMe;

    @ApiModelProperty(value = "记住我")
    private String tokenPrefix;

    /**
     * 权限
     */
    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
    @Setter
    private Collection<? extends GrantedAuthority> authorities;
}

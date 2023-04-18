package com.backend.security.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

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
    @ApiModelProperty(value = "是否首次登录")
    private boolean firstTimeLogin;
    @ApiModelProperty(value = "是否超管")
    private boolean superAdmin;
}

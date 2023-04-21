package com.backend.security.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author FPH
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String password;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String username;
}
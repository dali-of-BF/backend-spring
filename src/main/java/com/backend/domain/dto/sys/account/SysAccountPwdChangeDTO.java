package com.backend.domain.dto.sys.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author FPH
 * 账号密码修改类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAccountPwdChangeDTO {
    @NotBlank(message = "旧密码不可为空")
    private String oldPassword;
    @NotBlank(message = "新密码不可为空")
    private String newPassword;
}

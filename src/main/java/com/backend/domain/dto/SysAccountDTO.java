package com.backend.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author FPH
 * @since 2022年9月23日17:42:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAccountDTO {

    @ApiModelProperty("用户名")
    @Length(max = 50,message = "用户名长度不可超出五十字符")
    @NotBlank(message = "用户名不可为空")
    private String username;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不可为空")
    @Length(max = 11,message = "手机号长度不可超出11位")
    private String phone;

    @ApiModelProperty("性别")
    @NotBlank(message = "性别不可为空")
    private String gender;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("身份证号码")
    private String idCard;
}

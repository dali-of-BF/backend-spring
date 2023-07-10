package com.backend.domain.dto.sys;

import com.backend.domain.dto.common.StatusDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author FPH
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDTO extends StatusDTO {
    @ApiModelProperty("用户ID")
    @NotBlank(message = "用户ID不可为空")
    private String id;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("性别 男1女0")
    private String gender;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("头像")
    private String avatar;
}

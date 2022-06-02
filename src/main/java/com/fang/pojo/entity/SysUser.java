package com.fang.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.pojo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author FPH
 * @since 2022.04.27 03点46分
 * user表实体类
 */
@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户管理表")
public class SysUser extends BaseEntity {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态")
    private Integer status;
}

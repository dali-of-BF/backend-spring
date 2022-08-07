package com.fang.pojo.vo;

import com.fang.pojo.vo.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FPH
 * @since 2022年6月2日14:47:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO extends BaseVo {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态")
    private Integer status;
}

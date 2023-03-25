package com.fang.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常信息
 *
 * @author chenjp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "异常信息")
public class Error {

    @ApiModelProperty(value = "异常码")
    private String code;

    @ApiModelProperty(value = "异常提示信息")
    private String message;

}

package com.backend.domain.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FPH
 * @since 2023年7月10日16:01:41
 * 状态公共dto
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class StatusDTO {
    @ApiModelProperty("状态 1正常0关闭")
    private Integer status;
}

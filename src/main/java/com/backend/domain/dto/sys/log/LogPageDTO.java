package com.backend.domain.dto.sys.log;

import com.backend.domain.entity.base.BasePageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author FPH
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogPageDTO extends BasePageDTO {
    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("状态 1成功0失败")
    private String status;
}

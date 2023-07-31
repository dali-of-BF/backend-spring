package com.backend.domain.entity.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页
 * @author FPH
 * @since 2022年9月23日17:35:12
 */
@Data
@AllArgsConstructor
public class BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "当前页不能为空")
    @Min(value = 0L, message = "当前页必须大于或等于0")
    @ApiModelProperty(value = "当前页", required = true, dataType = "long")
    private Long page;

    @NotNull(message = "显示条数不能为空")
    @Min(value = 1L, message = "显示条数必须大于或等于1")
    @ApiModelProperty(value = "显示条数", required = true, dataType = "long")
    private Long size;

    public BasePageDTO(){
        this.page=1L;
        this.size=10L;
    }
}

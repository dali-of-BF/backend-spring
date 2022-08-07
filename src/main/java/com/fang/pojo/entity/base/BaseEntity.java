package com.fang.pojo.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author FPH
 * 实体类基类
 * @since 2022年6月2日11:44:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @NotNull(message = "创建时间不能为空")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 删除标志
     */
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

}

package com.backend.domain.entity.sys;

import com.backend.domain.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author FPH
 * @since 2023年5月19日10:54:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class SysMenu extends BaseEntity implements Serializable {


    /**
    * 菜单名称
    */
    @NotBlank(message="[菜单名称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("菜单名称")
    @Length(max= 50,message="编码长度不能超过50")
    private String menuName;
    /**
    * 1启用 0禁用
    */
    @ApiModelProperty("1启用 0禁用")
    private Integer status;

    /**
    * 父级id
    */
    @NotBlank(message="[父级id]不能为空")
    @Size(max= 36,message="编码长度不能超过36")
    @ApiModelProperty("父级id")
    @Length(max= 36,message="编码长度不能超过36")
    private String parentId;
    /**
    * 菜单层级
    */
    @NotNull(message="[菜单层级]不能为空")
    @ApiModelProperty("菜单层级")
    private Integer menuLevel;
    /**
    * 菜单类型（1模块2菜单3按钮）
    */
    @NotNull(message="[菜单类型（1模块2菜单3按钮）]不能为空")
    @ApiModelProperty("菜单类型（1模块2菜单3按钮）")
    private Integer menuType;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 路由
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("路由")
    @Length(max= 100,message="编码长度不能超过100")
    private String router;

}

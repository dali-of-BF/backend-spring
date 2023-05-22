package com.backend.domain.entity.sys;

import com.backend.domain.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FPH
 * @since 2023年5月19日10:54:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@TableName("sys_menu_resource")
@NoArgsConstructor
public class SysMenuResource extends BaseEntity implements Serializable {

    /**
    * 菜单ID
    */
    private String menuId;

    /**
     * 资源表ID
     */
    private String resourceId;
}

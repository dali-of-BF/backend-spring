package com.backend.domain.entity.sys;

import com.backend.domain.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author FPH
 * @since 2023年5月22日18:03:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role_menu")
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu extends BaseEntity {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID
     */
    private Integer menuId;
}

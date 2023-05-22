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
@TableName("sys_role")
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends BaseEntity {
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色状态 1启用0禁用
     */
    private Integer status;
    /**
     * 系统Code
     */
    private String systemCode;
}

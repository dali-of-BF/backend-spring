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
@TableName("sys_account_role")
@NoArgsConstructor
public class SysAccountResource extends BaseEntity implements Serializable {

    /**
    * 账号ID
    */
    private String accountId;

    /**
     * 角色ID
     */
    private String roleId;
}

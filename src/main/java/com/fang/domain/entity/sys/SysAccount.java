package com.fang.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FPH
 * @since 2022.04.27 03点46分
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_account")
public class SysAccount extends BaseEntity implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 1男0女
     */
    private Integer gender;

    /**
     * 状态 0关1开
     */
    private String status;

    /**
     * 身份证号码
     */
    private String idCard;
}

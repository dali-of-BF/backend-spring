package com.backend.domain.entity.sys;

import com.backend.domain.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FPH
 * @since 2022.04.27 03点46分
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 状态 1正常 0异常
     */
    private Integer status;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 所属系统
     */
    private String appId;

    /**
     * 是否超管 1是0不是
     */
    private String isSuper;
    /**
     * 头像
     */
    private String avatar;
}

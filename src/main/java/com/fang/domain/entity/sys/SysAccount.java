package com.fang.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
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
     * 性别
     */
    private String gender;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 身份证号码
     */
    private String idCard;
}

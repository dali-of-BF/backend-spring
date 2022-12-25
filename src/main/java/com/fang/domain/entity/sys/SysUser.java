package com.fang.domain.entity.sys;

import com.fang.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户表
 * @author ljw
 * @since 2022年12月25日17:43:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 0女1男
     */
    private Integer gender;
    /**
     * 状态 1正常2禁用
     */
    private Integer status;
    /**
     * 年龄
     */
    private Integer age;
}

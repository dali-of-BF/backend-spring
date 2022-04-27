package com.fang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.pojo.SysUser;

import java.util.List;

/**
 * @author FPH
 * @since 2022年4月27日04:06:07
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 通过id集合查询version集合
     * @param sysUser
     * @return
     */
    Integer editById(SysUser sysUser);
}

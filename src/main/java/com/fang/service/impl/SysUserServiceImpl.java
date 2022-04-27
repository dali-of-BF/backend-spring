package com.fang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.mapper.SysUserMapper;
import com.fang.pojo.SysUser;
import com.fang.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FPH
 * @since 2022年4月27日04:07:58
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public Integer editById(SysUser sysUser) {
        //先查询版本号
        Integer[] ids=new Integer[]{sysUser.getId()};
        List<Integer> version = sysUserMapper.queryVersionById(ids);
        sysUser.setVersion(version.get(0));
        return sysUserMapper.updateById(sysUser);
    }
}

package com.fang.service.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.domain.dto.SysUserCreateDTO;
import com.fang.domain.entity.sys.SysUser;
import com.fang.exception.BusinessException;
import com.fang.mapper.sys.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    private static final Integer MAX_AGE=150;
    public void saveEntity(SysUserCreateDTO dto){
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto,sysUser);
        if(sysUser.getAge()>MAX_AGE){
            throw new BusinessException("年龄不可超过150岁");
        }
        save(sysUser);
    }

    public void modifyEntity(SysUserCreateDTO dto){
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto,sysUser);
        if(sysUser.getAge()>MAX_AGE){
            throw new BusinessException("年龄不可超过150岁");
        }
        updateById(sysUser);
    }
}

package com.fang.service.impl;

import com.fang.pojo.entity.SysUser;
import com.fang.pojo.vo.SysUserVO;
import com.fang.service.manage.SysUserService;
import com.fang.utils.BeanUtils;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FPH
 * @since 2022年6月2日14:54:00
 */
@Service
@RequiredArgsConstructor
public class UserTestService {
    private final SysUserService userService;

    public List<SysUserVO> getList(){
        List<SysUser> list = userService.list();
        List<SysUserVO> sysUserVos = new ArrayList<>();
//        BeanUtils.copyToList(list,sysUserVos);
        return sysUserVos;
    }
}

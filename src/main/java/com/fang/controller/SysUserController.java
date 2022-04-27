package com.fang.controller;

import com.fang.common.AjaxResult;
import com.fang.pojo.SysUser;
import com.fang.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author FPH
 * @since 2022年4月27日04:09:41
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/selAll")
    public AjaxResult BaseSelectAll(){
        return AjaxResult.success(sysUserService.list());
    }
    @PutMapping("updateById")
    public AjaxResult updateById(SysUser sysUser){
        return AjaxResult.success(sysUserService.editById(sysUser));
    }
    @DeleteMapping("deleteByIds")
    public AjaxResult deleteById(Integer[] ids){
        return AjaxResult.success(sysUserService.removeByIds(Arrays.asList(ids)));
    }

    @PostMapping("add")
    public AjaxResult insertUser(@Valid SysUser sysUser){
        return AjaxResult.success(sysUserService.save(sysUser));
    }

}

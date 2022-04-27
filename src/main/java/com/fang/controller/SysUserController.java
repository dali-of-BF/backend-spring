package com.fang.controller;

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
    public List<SysUser> BaseSelectAll(){
        return sysUserService.list();
    }
    @PutMapping("updateById")
    public String updateById(SysUser sysUser){
        return sysUserService.editById(sysUser)+"";
    }
    @DeleteMapping("deleteByIds")
    public String deleteById(Integer[] ids){
        return sysUserService.removeByIds(Arrays.asList(ids))+"";
    }

    @PostMapping("add")
    public String insertUser(@Valid SysUser sysUser){
        return sysUserService.save(sysUser)+"";
    }

}

package com.fang.controller;

import com.fang.common.AjaxResult;
import com.fang.constants.WebConstants;
import com.fang.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FPH
 * @since 2022年8月7日14:40:04
 */
@RestController
@RequestMapping(WebConstants.SYS)
@RequiredArgsConstructor
@Api(tags = "系统用户")
public class SysUserController {
    private final SysUserService sysUserService;

    @ApiOperation("获取所有用户信息")
    public AjaxResult getUser(){
        return AjaxResult.success(sysUserService.list());
    }
}

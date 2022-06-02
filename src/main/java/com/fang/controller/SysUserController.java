package com.fang.controller;

import com.fang.common.AjaxResult;
import com.fang.pojo.vo.SysUserVO;
import com.fang.service.impl.UserTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FPH
 * @since 2022年4月27日04:09:41
 */
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor
@Api(tags = "用户管理")
public class SysUserController {
    private final UserTestService userTestService;

    @GetMapping("/selAll")
    @ApiOperation(value = "查询User表所有信息（无分页）",response = SysUserVO.class)
    public AjaxResult baseSelectAll() {
        return AjaxResult.success(userTestService.getList());
    }

}

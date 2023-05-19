package com.backend.controller.account;

import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.dto.BasePageDTO;
import com.backend.domain.entity.sys.SysMenu;
import com.backend.service.sys.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author FPH
 */
@RestController
@RequestMapping(ApiPathConstants.SYS_MENU)
@RequiredArgsConstructor
@Api(tags = "系统菜单管理")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @GetMapping("list")
    @ApiOperation("获取分页集合（没有根据角色权限划分）")
    public Result<List<SysMenu>> list(){
        return Result.success(sysMenuService.list());
    }
    @PostMapping("save")
    @ApiOperation("保存菜单")
    public Result<List<SysMenu>> save(@Valid BasePageDTO dto){
        return Result.success(sysMenuService.list());
    }
}

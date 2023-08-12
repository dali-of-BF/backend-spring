package com.backend.controller.pc.sys;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.entity.sys.SysMenu;
import com.backend.domain.vo.pc.MenuTreeVO;
import com.backend.service.sys.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FPH
 */
@RestController
@RequestMapping(ApiPathConstants.SYS_MENU)
@RequiredArgsConstructor
@Api(tags = "菜单管理")
@Log
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @GetMapping("list")
    @ApiOperation("获取分页集合")
    public Result<List<MenuTreeVO>> list(){
        return Result.success(sysMenuService.getMenuTree());
    }
    @PostMapping("save")
    @ApiOperation("保存菜单")
    public Result<List<SysMenu>> save(){
        return Result.success(sysMenuService.list());
    }

    @DeleteMapping("delete")
    @ApiOperation("删除菜单")
    public Result<List<SysMenu>> delete(){
        return Result.success(sysMenuService.list());
    }

}

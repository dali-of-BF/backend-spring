package com.backend.controller.common.menu;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.entity.sys.SysMenu;
import com.backend.service.sys.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FPH
 * @since 2023年8月4日16:48:41
 * 菜单重构控制器
 */
@RestController
@Slf4j
@RequestMapping(ApiPathConstants.COM_MENU)
@RequiredArgsConstructor
@Api(tags = "菜单重构控制器")
@Log(excludeMethodType = {})
public class MenuFlushController {
    private final SysMenuService sysMenuService;
    @GetMapping("/refactor-menu")
    @ApiOperation("重构菜单")
    public Result<List<SysMenu>> refactorMenu() {
        return Result.success(sysMenuService.refactorMenu()) ;
    }
}

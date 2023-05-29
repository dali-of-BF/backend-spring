package com.backend.controller.pc.sys;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.entity.sys.SysLog;
import com.backend.service.sys.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FPH
 */
@RestController
@RequestMapping(ApiPathConstants.SYS_LOG)
@RequiredArgsConstructor
@Api(tags = "系统日志管理")
@Log
public class SysLogController {

    private final SysLogService sysLogService;

    @GetMapping("list")
    @ApiOperation("获取日志集合")
    public Result<List<SysLog>> list(){
        return Result.success(sysLogService.list(new LambdaQueryWrapper<SysLog>()
                .orderByDesc(SysLog::getOperTime)));
    }
}
